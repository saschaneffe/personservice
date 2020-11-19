var app = new Vue({
    el: "#app",
    data: {
        persons: [{
            id: 1,
            firstname: "Max"
        }],
        editData: {
            id: null,
            firstName: null,
            lastName: null,
            mode: 0
        }
    },
    methods: {
        addPerson: function () {
            this.updatePerson(1);
        },
        editPerson: function(personToEdit) {
            this.updatePerson(2, personToEdit);
        },
        updatePerson: function (mode, personToEdit) {
            if(mode !== 0) {
                if(mode === 2) {
                    this.editData.id = personToEdit.id;
                    this.editData.firstName = personToEdit.firstName;
                    this.editData.mode = 2;
                } else {
                    this.editData.id = null;
                    this.editData.firstName = null;
                    this.editData.mode = 1;
                }

                $("#personEditModal").modal();
            }
        }
    }
})