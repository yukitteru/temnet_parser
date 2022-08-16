// noinspection JSUnresolvedFunction
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
let group, username, text, dTable, id;
let totalCount;
const option_data = [
    {id: "in_progress", value: "ЗАЯВКА В РАБОТЕ"},
    {id: "rejected", value: "ЗАКРЫТА ЗАЯВКА"},
    {id: "close", value: "ЗАЯВКА ОТКЛОНЕНА"}
];
const groupsStore = new webix.DataCollection({
    url: "api/groups",
});

let form =
    {
        view: "form",
        id: "search",
        borderless: true,
        elementsConfig: {
            labelWidth: 130,
            on: {
                "onChange": function (newv, oldv) {
                    this.validate();
                }
            }
        },
        elements: [
            {
                view: "combo",
                id: "group",
                name: "group",
                label: "Группа:",
                placeholder: "Оставьте пустым для получения статистики по всем группам и пользователям",
                clear: 1,
                options: groupsStore,
                on: {
                    onAfterRender: function () {
                        $$('users').disable();
                    },
                    onChange: function () {
                        if ($$("group").getText() !== " " && $$("group").getText() !== "") {
                            let usersStore = new webix.DataCollection({
                                url: "api/users/" + getPropertyValue("group"),
                            });
                            $$("users").enable();
                            $$("users").define("options", usersStore);
                        } else $$("users").disable();
                    }
                }
            },
            {
                view: "combo",
                labelWidth: 130,
                id: "users",
                name: "users",
                label: "Пользователь:",
                placeholder: "Оставьте пустым для получения статистики по всей группе",
                clear: 1,
            },
            {
                view: "datepicker",
                label: "От:",
                id: "from",
                name: "from",
                required: true,
                validate: webix.rules.isNotEmpty(),
                invalidMessage: "Поле \"От\" не может быть пустым",
                stringResult: true
            },
            {
                view: "datepicker",
                label: "До:",
                id: "to",
                name: "to",
                required: true,
                validate: webix.rules.isNotEmpty(),
                invalidMessage: "Поле \"До\" не может быть пустым",
                stringResult: true

            },
            {
                view: "combo",
                name: "status",
                id: "status",
                label: "Сообщение",
                value: '',
                options: {
                    filter: function (item, value) {
                        text = $$("status").getText()
                        return item.value.toString().toLowerCase().indexOf(value.toLowerCase()) !== -1;
                    },
                    data: option_data,

                },
                on: {
                    onChange: function () {
                        text = $$("status").getText()
                    }
                }
            },
            {
                view: "button", value: "Поиск", width: 100, align: "center", click: function () {
                    if (this.getParentView().validate())
                        if (!dTable) {
                            id = Math.random()
                            webix.message({type: "info", text: "Данные загружаются. Ожидайте"})
                            dTable = createTable();
                            $$("users").setValue(-1);
                            $$("status").setValue("");
                        } else {
                            $$("orgList" + id.toString()).getTopParentView().hide();
                            id = Math.random();
                            webix.message({type: "info", text: "Данные загружаются. Ожидайте"})
                            dTable = createTable();
                            $$("users").setValue(-1);
                            $$("status").setValue("");
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

function buildRepositoryLink() {
    if (getPropertyValue("group") === "" || $$("group").getText() === "") {
        return "api/archive/search/all/" + getPropertyValue("from") + "/" + getPropertyValue("to") + "/" + text;
    }
    return getPropertyValue("users") === "" || $$("users").getText() === "" ?
        "api/archive/search/" + getPropertyValue("group") + "/all" + "/" + date_formatter(getPropertyValue("from")) + "/" + date_formatter(getPropertyValue("to")) + "/" + text :
        "api/archive/search/" + getPropertyValue("group") + "/" + getPropertyValue("users") + "/" + date_formatter(getPropertyValue("from")) + "/" + date_formatter(getPropertyValue("to")) + "/" + text;


}

function createTable() {
    return webix.ui({
            rows: [
                {
                    view: "form",
                    id: "export" + id.toString(),
                    borderless: true,
                    css: "toolbar",
                    paddingY: 5,
                    paddingX: 10,
                    cols: [
                        {
                            view: "label"
                        },
                        {
                            view: "button", id: "excel" + id.toString(), label: "Excel", width: 95, click: function () {
                                webix.toExcel(
                                    [$$("orgList" + id.toString()), $$("list" + id.toString())],
                                    {filename: $$("group").getText(), sheets: ["Отдельно", "Всего"]}
                                );
                            }
                        },
                        {
                            view: "button", id: "pdf" + id.toString(), label: "PDF", width: 95, click: function () {
                                webix.toPDF(
                                    [$$("orgList" + id.toString()), $$("list" + id.toString())],
                                    {filename: $$("group").getText()}
                                );
                            }
                        },
                    ]
                },
                {
                    view: "pager",
                    id: "orgPager" + id.toString(),
                    size: 15,
                    group: 15,
                    template: "{common.first()}{common.prev()}{common.pages()}{common.next()}{common.last()}"
                },
                {
                    id: "orgList" + id.toString(),
                    view: "datatable",
                    borderless: false,
                    height: 150,
                    width: 500,
                    editable: false,
                    datafetch: 100,
                    scrollX: false,
                    columns: [
                        {id: "username", header: "Имя пользователя", width: 200},
                        {id: "count", header: "Количество", width: 100}
                    ],
                    on: {
                        onBeforeLoad: function () {
                            $$("search").disable()
                            webix.extend($$("orgList" + id.toString()), webix.ProgressBar);
                            $$("orgList" + id.toString()).showProgress({
                                hide: true,
                                delay: 40000
                            });
                            $$("excel" + id.toString()).disable()
                            $$("pdf" + id.toString()).disable()
                        },
                        onAfterLoad: function () {
                            $$("search").enable();
                            $$("excel" + id.toString()).enable();
                            $$("pdf" + id.toString()).enable();
                        }
                    },
                    url: "resource->" + "http://localhost:8080/" + buildRepositoryLink(),
                    pager: "orgPager" + id.toString(),
                },
                {
                    id: "list" + id.toString(),
                    view: "list",
                    template: "#username#: #count#",
                    scroll: false,
                    type: {
                        height: 30
                    },
                    url: "resource->" +
                        "http://localhost:8080/" + buildRepositoryLink() + "/totalCount"
                },
            ]
        }
    )
}




