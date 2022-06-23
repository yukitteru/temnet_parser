// noinspection JSUnresolvedFunction

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

function getPropertyValue(id) {
    return $$(id).getValue();
}

function date_formatter(date) {
    let format = webix.Date.dateToStr("%Y-%m-%d 00:00:00");
    return format(date);
}

let from = {
    view: "datepicker",
    label: "От",
    id: "from",
    name: "from",
    stringResult: true
};

let to = {
    view: "datepicker",
    label: "До",
    id: "to",
    name: "to",
    stringResult: true
}

let statusForm = {
    view: "label",
    width: 50
};

function buildRepositoryLink() {
    return "api/archive/search/" + getPropertyValue("organization") + "/" + date_formatter(getPropertyValue("from")) + "/" + date_formatter(getPropertyValue("to")) + "/" + getPropertyValue("status")
}

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
let form = {
    view: "form",
    id: "search",
    elementsConfig: {
        labelWidth: 130
    },
    elements: [
        {
            view: "text",
            id: "organization",
            name: "organization",
            label: "Организация",
            value: ""
        },
        from, to, statusForm, options,
        {
            view: "button", value: "Поиск", click: function () {
                webix.ui({
                    rows: [
                        {
                            id: "organizationList",
                            view: "datatable",
                            columns: [
                                {id: "username", header: "Имя пользователя", width: 200},
                                {id: "count", header: "Количество", width: 200}
                            ],
                            url: 'resource->' +
                                'http://localhost:8080/' + buildRepositoryLink(),

                            autowidth: true,
                            autoheight: true,
                            editable: false,
                            datafetch: 1000000,
                            pager: 'organizationPager'
                        },
                        {
                            view: "pager",
                            id: 'organizationPager',
                            size: 10,
                            group: 10,
                            template: "{common.first()}{common.prev()}{common.pages()}{common.next()}{common.last()}"
                        }
                    ]
                })
            }
        },
    ]
};





