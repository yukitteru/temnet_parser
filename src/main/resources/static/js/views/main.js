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

statusForm = {
    view: "label",
    width: 50
};

let option_data = [
    {id: 'in_progress', value: "ЗАЯВКА В РАБОТЕ"},
    {id: 'rejected', value: "ЗАКРЫТА ЗАЯВКА"},
    {id: 'close', value: "ЗАЯВКА ОТКЛОНЕНА"}
];


let options = {
    view: "combo",
    name: "status",
    id: "status",
    label: 'Сообщение',
    value: '',
    required: true,
    validate: webix.rules.isNotEmpty(),
    invalidMessage: "Поле 'Группа' не может быть пустым",
    options: {
        filter: function (item, value) {
            text = $$('status').getText()
            return item.value.toString().toLowerCase().indexOf(value.toLowerCase()) !== -1;
        },
        data: option_data,

    },
    on: {
        onChange: function () {
            text = $$('status').getText()
        }
    }

};

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
            view: "combo",
            id: 'group',
            name: 'group',
            label: 'Группа:',
            placeholder: 'Выберите "ВСЕ" для поиска по всем группам',
            clear: 1,
            required: true,
            validate: webix.rules.isNotEmpty(),
            invalidMessage: "Поле 'Группа' не может быть пустым",
            options: groupsStore,
            on: {
                onChange: function () {
                    if ($$('group').getText() !== 'ВСЕ' && $$('group').getText() !== '') {
                        let usersStore = new webix.DataCollection({
                            url: 'api/users/' + getPropertyValue('group'),
                            scheme: {
                                $init: function (obj) {
                                    obj.value = obj.username;
                                }
                            }
                        });
                        if (!!$$('search').queryView({id: 'users'})) {
                            $$('search').removeView('users');
                            let pos = $$("search").index($$("group")) + 1;
                            $$("search").addView({
                                view: "combo",
                                labelWidth: 130,
                                id: 'users',
                                name: 'users',
                                label: 'Пользователь:',
                                placeholder: 'Оставьте пустым для получения статистики по всей группе',
                                clear: 1,
                                options: usersStore,
                            }, pos);
                        } else {
                            let pos = $$("search").index($$("group")) + 1;
                            $$("search").addView({
                                view: "combo",
                                labelWidth: 130,
                                id: 'users',
                                name: 'users',
                                label: 'Пользователь:',
                                placeholder: 'Оставьте пустым для получения статистики по всей группе',
                                clear: 1,
                                options: usersStore
                            }, pos);
                        }
                    } else $$("search").removeView("users");
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
                        $$('users').setValue(-1);
                        $$('status').setValue('');
                    } else {
                        $$('orgList' + id.toString()).getTopParentView().hide();
                        id = Math.random();
                        webix.message({type: 'info', text: 'Данные загружаются. Ожидайте'})
                        dTable = createTable();
                        $$('users').setValue(-1);
                        $$('status').setValue('');
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
    if ($$('group').getText() === 'ВСЕ') {
        return "api/archive/search/all/" + date_formatter(getPropertyValue("from")) + "/" + date_formatter(getPropertyValue("to")) + "/" + text;
    } else {
        return getPropertyValue('users') === '' || $$('users').getText() === '' ?
            "api/archive/search/" + getPropertyValue('group') + '/-1' + "/" + date_formatter(getPropertyValue("from")) + "/" + date_formatter(getPropertyValue("to")) + "/" + text :
            "api/archive/search/" + getPropertyValue('group') + '/' + getPropertyValue('users') + "/" + date_formatter(getPropertyValue("from")) + "/" + date_formatter(getPropertyValue("to")) + "/" + text;

    }
}

function createTable() {
    return webix.ui({
            rows: [
                {
                    id: 'orgList' + id.toString(),
                    view: "datatable",
                    columns: [
                        {id: 'username', header: "Имя пользователя", width: 200},
                        {id: 'count', header: "Количество", width: 100}
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
                },
                {
                    id: "list1",
                    view:"list",
                    template:"#username#: #count#",
                    type:{
                        height:65
                    },
                    select:true,
                    url: 'resource->' +
                            'http://localhost:8080/' + buildRepositoryLink() + "/totalCount"

                },
            ],
        }
    )
}





