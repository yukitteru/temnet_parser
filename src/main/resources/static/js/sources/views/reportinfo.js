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
        let active = 0;
        let numberOfAccounts = 0;
        let finished = 0;
        let inProgress = 0;
        let rejected = 0;
        let messageCount = 0;
        let index = 0;
        return {
            rows: [
                {
                    id: "datatable",
                    view: "datatable",
                    localId: "datatable",
                    math: true,
                    url: "resource->http://localhost:9090/api/v1/report/top/" + startDate + "/" + endDate,
                    select: true,
                    columns: [
                        {id: "index", header: _("#"), sort: "int", fillspace: true},
                        {
                            id: "groupName",
                            header: _("Группа пользователей"),
                            sort: "string",
                            fillspace: true,
                            css: {"text-decoration": "underline", "color": "#000000"}
                        },
                        {id: "activeUsers", header: _("Активные"), sort: "int", fillspace: true},
                        {id: "numberOfAccounts", header: _("Уч. записей Miranda"), sort: "int", fillspace: true},
                        {id: "finished", header: _("Закрыто"), sort: "int", fillspace: true},
                        {id: "inProgress", header: _("Отложено"), sort: "int", fillspace: true},
                        {id: "rejected", header: _("Отклонено"), sort: "int", fillspace: true},
                        {id: "messageCount", header: _("Всего в диалоге"), sort: "int", fillspace: true},
                    ],
                    on: {
                        "data->onStoreUpdated": function () {
                            active = 0;
                            numberOfAccounts = 0;
                            messageCount = 0;
                            finished = 0;
                            rejected = 0;
                            inProgress = 0;
                            index = 0;
                            this.data.each(function (obj, i) {
                                obj.index = i + 1;
                                active += obj.activeUsers;
                                numberOfAccounts += obj.numberOfAccounts;
                                finished += obj.finished;
                                inProgress += obj.inProgress;
                                rejected += obj.rejected;
                                messageCount += obj.messageCount;
                                index = obj.index;
                            });
                        },

                        onBeforeLoad: function () {
                            this.getTopParentView().disable();
                        },
                        onAfterLoad: function () {
                            this.add({
                                index: index,
                                groupName: "ВСЕГО",
                                activeUsers: active,
                                numberOfAccounts: numberOfAccounts,
                                finished: finished,
                                inProgress: inProgress,
                                rejected: rejected,
                                messageCount: messageCount
                            }, index);
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
        let startDate = "2022-01-01%2000:00:00"
        let endDate = "2022-09-09%2000:00:00"
        this.on(this.app, "search:report", (start, end) => {
            startDate = date_formatter(start);
            endDate = date_formatter(end);
            grid.hideOverlay();
            grid.clearAll();
            grid.define("url", "resource->http://localhost:9090/api/v1/report/top/" + start + "/" + end);
        });
        grid.attachEvent("onItemClick", function (id, e, node) {
            if (id.column === "groupName" && node.innerText !== "ВСЕГО") {
                username = node.innerText;
                let index = 0;
                let finished = 0;
                let inProgress = 0;
                let rejected = 0;
                let totalMessages = 0;
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
                        {id: "inProgress", header: ("Отложено заявок"), sort: "int", fillspace: true},
                        {id: "rejected", header: ("Отклонено заявок"), sort: "int", fillspace: true},
                        {
                            id: "totalMessages",
                            header: ("Всего в диалоге"),
                            sort: "int",
                            fillspace: true
                        }
                    ],
                    on: {
                        "data->onStoreUpdated": function () {
                            this.data.each(function (obj, i) {
                                obj.index = i + 1;
                                index = obj.index;
                                finished += obj.finished;
                                inProgress += obj.inProgress;
                                rejected += obj.rejected;
                                totalMessages += obj.totalMessages;
                            });

                        },
                        onAfterLoad: function () {
                            this.add({
                                index: index,
                                username: "ВСЕГО",
                                finished: finished,
                                inProgress: inProgress,
                                rejected: rejected,
                                totalMessages: totalMessages
                            }, index);
                            this.getTopParentView().enable();
                        },
                    }
                })
                grid.hide();
            }
        });


    }

}
