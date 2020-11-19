Vue.filter('formatDate', function(value) {
    if (value) {
        return moment(String(value)).format('YYYY-MM-DD')
    }
});

var app = new Vue({
    el: "#app",
    data: {
        persons: [],
        editData: {
            id: null,
            firstName: null,
            lastName: null,
            gender: null,
            birthday: null,
            size: null,
            weight: null,
            mode: 0
        },
        infoData: {
          text: ""
        },
        genders: ["Male", "Female"]
    },
    methods: {
        openAddPerson: function () {
            this.openUpdatePerson(1);
        },
        openEditPerson: function(personToEdit) {
            this.openUpdatePerson(2, personToEdit);
        },
        openUpdatePerson: function (mode, personToEdit) {
            if(mode !== 0) {

                if(mode === 2) {
                    this.editData = {
                        id: personToEdit.id,
                        firstName: personToEdit.firstName,
                        lastName: personToEdit.lastName,
                        gender: personToEdit.gender,
                        birthday: personToEdit.birthday,
                        size: personToEdit.size,
                        weight: personToEdit.weight,
                        mode: 2
                    };
                } else {
                    this.editData = {
                        id: null,
                        firstName: null,
                        lastName: null,
                        gender: null,
                        birthday: null,
                        size: null,
                        weight: null,
                        mode: 1
                    };
                }

                if(this.editData.birthday !== null) {
                    this.editData.birthday = moment(this.editData.birthday).format("YYYY-MM-DD");
                }

                $("#personEditModal").modal();
            }
        },
        updatePerson: function () {
            let self = this;
            $("#personEditModal").modal('hide');
            let method = self.editData.mode === 1 ? 'POST' : 'PUT';
            let url = "person" + (self.editData.mode === 1 ? "" : "/" + self.editData.id);
            let data = JSON.stringify(self.editData);

            $.ajax(url, {
                method: method,
                data: data,
                contentType: 'application/json'
            }).then(function() {
                self.infoData.text = "The person was successfully edited";
                self.infoData.title = "Edit successful";
                self.refreshPersons();
                $("#infoTextModal").modal();
            }).fail(function (jqXHR, textStatus, errorThrown) {
                self.infoData.text = "An error occured during edit of person.\n" + textStatus + " " + errorThrown;
                self.infoData.title = "Edit failed";
                $("#infoTextModal").modal();
            });
        },
        refreshPersons: function () {
            let self = this;
            $.get({ url: 'person', dataType: 'json', success: function (content) {
                self.persons = content.map(element => {
                    if(element.birthday !== null) {
                        element.birthday = moment(element.birthday).toDate();
                    }

                    return element;
                });
            }})
        },
        openDeletePerson: function (id) {
            this.editData = {
                id: id,
                firstName: null,
                lastName: null,
                gender: null,
                birthday: null,
                size: null,
                weight: null,
                mode: 3
            };
            $("#personDeleteModal").modal();
        },
        deletePerson: function () {
            $("#personDeleteModal").modal('hide');
            let self = this;
            if(self.editData.mode === 3) {
                $.ajax({
                    url: "person/" + self.editData.id,
                    method: "DELETE"
                }).then(function () {
                    self.infoData.text = "The person was successfully deleted";
                    self.infoData.title = "Deletion successful";
                    self.refreshPersons();
                    $("#infoTextModal").modal();
                }).fail(function (jqXHR, textStatus, errorThrown) {
                    self.infoData.text = "An error occured during deletion of person.\n" + errorThrown;
                    self.infoData.title = "Deletion failed";
                    $("#infoTextModal").modal();
                })
            }
        }
    }
});

app.refreshPersons();