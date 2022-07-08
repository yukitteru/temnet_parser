webix.i18n.setLocale("ru-RU");
define(function () {
    return {
        rows: [
            {
                view: "toolbar",
                css: "webix_dark",
                paddingX: 17,
                elements: [
                    {view: "label", label: "Поиск"},
                    {},
                    {view: "icon", icon: "mdi mdi-help-circle-outline"}
                ]
            },
            form

        ]
    }
})

let from = {
    view: "datepicker",
    label: "От",
    id: "from",
    name: "from",
    required: true,
    validate: webix.rules.isNotEmpty(),
    invalidMessage:"Поле 'От' не может быть пустым",
    stringResult: true
};

let to = {
    view: "datepicker",
    label: "До",
    id: "to",
    name: "to",
    required: true,
    validate: webix.rules.isNotEmpty(),
    invalidMessage:"Поле 'До' не может быть пустым",
    stringResult: true
}

let statusForm = {
    view: "label",
    width: 50
};

let options = {
    view: "segmented",
    name: "status",
    id: "status",
    options:
        [
            {
                id: "in_progress",
                value: "ЗАЯВКА В РАБОТЕ",
            },
            {
                id: "rejected",
                value: "ЗАЯВКА ОТКЛОНЕНА"
            },
            {
                id: "close",
                value: "ЗАЯВКА ЗАКРЫТА"
            },
        ]
};

let dTable;
let id;

let form = {
    view: "form",
    id: "search",
    elementsConfig: {
        labelWidth: 130,
        on: {
            'onChange': function (newv, oldv) {
                this.validate();
            }
        }
    },
    elements: [
        {
            view: "text",
            id: "organization",
            name: "organization",
            label: "Организация",
            required: true,
            validate: webix.rules.isNotEmpty(),
            invalidMessage:"Поле 'Организация' не может быть пустым",
            value: ""
        },
        from, to, statusForm, options,
        {
            view: "button", value: "Поиск", click: function () {
                if (this.getParentView().validate())
                    if (!dTable) {
                        id = Math.random()
                        dTable = createTable()
                    } else {
                        $$('orgList' + id.toString()).getTopParentView().hide();
                        id = Math.random();
                        dTable = createTable();
                    }
                else {
                    webix.message({type: "error", text: "Произошла ошибка при вводе данных"});
                }
            }
        },
    ]
}

function getPropertyValue(id) {
    return $$(id).getValue();

}

function date_formatter(date) {
    let format = webix.Date.dateToStr("%Y-%m-%d 00:00:00");
    return format(date);

}

function getPagerId() {
    return 'organizationPager' +
        getPropertyValue("organization") +
        "/" +
        date_formatter(getPropertyValue("from")) +
        "/" +
        date_formatter(getPropertyValue("to")) +
        "/" +
        getPropertyValue("status");

}

function getListId() {
    return 'organizationList' +
        getPropertyValue("organization") +
        "/" +
        date_formatter(getPropertyValue("from")) +
        "/" +
        date_formatter(getPropertyValue("to")) +
        "/" +
        getPropertyValue("status");

}

function buildRepositoryLink() {
    return "api/archive/search/" +
        getPropertyValue("organization") +
        "/" +
        date_formatter(getPropertyValue("from")) +
        "/" +
        date_formatter(getPropertyValue("to")) +
        "/" +
        getPropertyValue("status");

}

function createTable() {
    return webix.ui({
        rows: [
            {
                id: 'orgList' + id.toString(),
                view: "datatable",
                columns: [
                    {id: "username", header: "Имя пользователя", width: 200},
                    {id: "count", header: "Количество", width: 100}
                ],

                url: 'resource->' +
                    'http://localhost:8080/' + buildRepositoryLink(),

                autowidth: true,
                autoheight: true,
                editable: false,
                datafetch: 100,
                pager: 'orgPager' + id.toString()
            },
            {
                view: "pager",
                id: 'orgPager' + id.toString(),
                size: 5,
                group: 5,
                template: "{common.first()}{common.prev()}{common.pages()}{common.next()}{common.last()}"
            }
        ]
    })
}





