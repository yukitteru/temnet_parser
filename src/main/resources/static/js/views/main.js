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
    invalidMessage: "Поле 'От' не может быть пустым",
    stringResult: true
};


let to = {
    view: "datepicker",
    label: "До",
    id: "to",
    name: "to",
    required: true,
    validate: webix.rules.isNotEmpty(),
    invalidMessage: "Поле 'До' не может быть пустым",
    stringResult: true
}

let messageSearchField = {
        view: "text",
        id: "message",
        name: "message",
        label: "Поиск по сообщению",
        value: ""
    },

    statusForm = {
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

let groupsStore = new webix.DataCollection({
    url: 'api/groups',
    scheme: {
        $init: function (obj) {
            obj.value = obj.name;
        }
    }
});

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
            view: "richselect",
            id: 'group',
            name: 'group',
            label: 'Группа:',
            placeholder: 'Оставьте пустым для получения статистики по всем группам',
            value: 'all',
            clear:1,
            options: groupsStore,
            on: {
                onChange: function () {
                    let pos = $$("search").index($$("group")) + 1;
                    $$("search").removeView("users")
                    let usersStore = new webix.DataCollection({
                        url: 'api/users/' + getPropertyValue('group'),
                        scheme: {
                            $init: function (obj) {
                                obj.value = obj.username;
                            }
                        }
                    });
                    $$("search").addView({
                        view: "richselect",
                        labelWidth: 130,
                        id: 'users',
                        name: 'users',
                        label: 'Пользователь:',
                        placeholder: 'Оставьте пустым для получения статистики по всей группе',
                        value: -1,
                        clear:1,
                        options: usersStore
                    }, pos);
                }
            }
        },

        from, to, statusForm, options,
        {
            view: "button", value: "Поиск", click: function () {
                if (this.getParentView().validate())
                    if (!dTable) {
                        id = Math.random()
                        dTable = createTable();
                        $$('users').setValue('all')
                    } else {
                        $$('orgList' + id.toString()).getTopParentView().hide();
                        id = Math.random();
                        dTable = createTable();
                        $$('users').setValue('all')
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
    if (getPropertyValue('users') === -1) {
        return "api/archive/search/" + getPropertyValue('group') + '/-1' +
            "/" +
            date_formatter(getPropertyValue("from")) +
            "/" +
            date_formatter(getPropertyValue("to")) +
            "/" +
            getPropertyValue("status");
    } else {
        return "api/archive/search/" + getPropertyValue('group') + '/' + getPropertyValue('users') +
            "/" +
            date_formatter(getPropertyValue("from")) +
            "/" +
            date_formatter(getPropertyValue("to")) +
            "/" +
            getPropertyValue("status");
    }

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





