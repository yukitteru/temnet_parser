import {JetView} from "webix-jet";
import {getTopReport} from "../models/topreport";
import {date_formatter} from "../util/dateformat";
import {getDetailReport} from "../models/detailreport";

// noinspection JSUnresolvedFunction
export default class ReportInfoView extends JetView {
    config() {
        const _ = this.app.getService("locale")._;
        webix.extend(webix.ui.datatable, webix.ProgressBar)
        let startDate = "2018-01-01%2000:00:00"
        let endDate = "2022-09-09%2000:00:00"
        return {
            rows: [
                {
                    id: "datatable",
                    view: "datatable",
                    localId: "datatable",
                    url: "resource->http://localhost:9090/api/v1/report/top/" + startDate + "/" + endDate,
                    select: true,
                    columns: [
                        {id: "index", header: _("#"), sort: "int", fillspace: true},
                        {
                            id: "groupName", header: _("Группа пользователей"), sort: "string", fillspace: true, css: {
                                "text-decoration": "underline",
                                "color": "#000000"
                            }
                        },
                        {id: "activeUsers", header: _("Активные"), sort: "int", fillspace: true},
                        {id: "numberOfAccounts", header: _("Уч. записей Miranda"), sort: "int", fillspace: true},
                        {id: "messageCount", header: _("Кол-во сообщений"), sort: "int", fillspace: true},
                    ],
                    on: {
                        "data->onStoreUpdated": function () {
                            this.data.each(function (obj, i) {
                                obj.index = i + 1;
                            });

                        },
                        onBeforeLoad: function () {
                            this.getTopParentView().disable();
                        },
                        onAfterLoad: function () {
                            this.getTopParentView().enable();
                        },
                    },

                },

            ]
        };
    }

    init() {
        let grid = this.$$("datatable");
        let parentView = this.getParentView();
        let username;
        let startDate = "2018-01-01%2000:00:00"
        let endDate = "2022-09-09%2000:00:00"
        this.on(this.app, "search:report", (start, end) => {
            startDate = date_formatter(start);
            endDate = date_formatter(end);
            grid.hideOverlay();
            grid.clearAll();
            grid.define("url", "resource->http://localhost:9090/api/v1/report/top/" + start + "/" + end);
        });
        grid.attachEvent("onItemClick", function (id, e, node) {
            if (id.column === "groupName") {
                username = node.innerText;
                this.getParentView().addView({
                    id: "button",
                    view: "button",
                    label: "Назад",
                    click: function () {
                        this.getParentView().removeView("datatable2");
                        this.getParentView().removeView("button");
                        grid.show();
                    }
                })
                this.getParentView().addView({
                    id: "datatable2",
                    view: "datatable",
                    localId: "datatable2",
                    url: "resource->http://localhost:9090/api/v1/report/detail/" + username + "/" + startDate + "/" + endDate,
                    columns: [
                        {id: "index", header: ("#"), sort: "string", fillspace: true},
                        {id: "username", header: ("Имя пользователя"), sort: "string", fillspace: true},
                        {id: "finished", header: ("Закрыто заявок"), sort: "int", fillspace: true},
                        {id: "inProgress", header: ("Заявок взято в работу"), sort: "int", fillspace: true},
                        {id: "rejected", header: ("Отклонено заявок"), sort: "int", fillspace: true},
                        {
                            id: "totalMessages",
                            header: ("Всего сообщений от пользователя"),
                            sort: "int",
                            fillspace: true
                        }
                    ],
                    on: {
                        "data->onStoreUpdated": function () {
                            this.data.each(function (obj, i) {
                                obj.index = i + 1;
                            });

                        }
                    }
                })
                grid.hide();
            }
        });


    }

}
