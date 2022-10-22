import {JetView} from "webix-jet";
import {getTopReport} from "../models/topreport";
import {date_formatter} from "../util/dateformat";
import {getDetailReport} from "../models/detailreport";

// noinspection JSUnresolvedFunction,JSPotentiallyInvalidUsageOfThis,JSVoidFunctionReturnValueUsed
export default class ReportInfoView extends JetView {
    config() {
        const _ = this.app.getService("locale")._;
        webix.extend(webix.ui.datatable, webix.ProgressBar)
        let startDate = "2022-01-01%2000:00:00"
        let endDate = "2022-12-31%2000:00:00"
        let active = 0;
        let numberOfAccounts = 0;
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
                        {id: "finished", header: ("Закрыто заявок"), sort: "int", fillspace: true},
                        {id: "inProgress", header: ("Заявок отложено"), sort: "int", fillspace: true},
                        {id: "rejected", header: ("Отклонено заявок"), sort: "int", fillspace: true},
                        {id: "messageCount", header: _("Кол-во сообщений"), sort: "int", fillspace: true},
                    ],
                    pager: 'groupPager',
                    on: {
                        "data->onStoreUpdated": function () {
                            active = 0;
                            numberOfAccounts = 0;
                            messageCount = 0;
                            index = 0;
                            this.data.each(function (obj, i) {
                                obj.index = i + 1;
                                active += obj.activeUsers;
                                numberOfAccounts += obj.numberOfAccounts;
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
        let _topReport = this.$$("datatable");
        let parentView = this.getParentView();
        let username;
        let startDate = "2022-01-01%2000:00:00"
        let endDate = "2022-12-31%2000:00:00"
        this.on(this.app, "search:report", (start, end) => {
            startDate = date_formatter(start);
            endDate = date_formatter(end);
            _topReport.hideOverlay();
            _topReport.clearAll();
            _topReport.define("url", "resource->http://localhost:9090/api/v1/report/top/" + start + "/" + end);
        });
        _topReport.attachEvent("onItemClick", function (id, e, node) {
            if (id.column === "groupName" && node.innerText !== 'ВСЕГО') {
                username = node.innerText;
                let index = 0;
                let finished = 0;
                let inProgress = 0;
                let rejected = 0;
                let totalMessages = 0;
                let parentView1 = this.getParentView();
                this.getParentView().addView({
                    id: "back_button",
                    view: "button",
                    label: "Назад",
                    click: function () {
                        parentView1.removeView("datatable2");
                        parentView1.removeView("back_button");
                        _topReport.show();
                    }
                })
                this.getParentView().addView({
                    id: "datatable2",
                    view: "datatable",
                    localId: "datatable2",
                    url: "resource->http://localhost:9090/api/v1/report/detail/" + username + "/" + startDate + "/" + endDate,
                    columns: [
                        {id: "index", header: ("#"), sort: "int", fillspace: true},
                        {id: "username", header: ("Имя пользователя"), sort: "string", fillspace: true, css: {"text-decoration": "underline", "color": "#000000"}},
                        {id: "finished", header: ("Закрыто заявок"), sort: "int", fillspace: true},
                        {id: "inProgress", header: ("Заявок отложено"), sort: "int", fillspace: true},
                        {id: "rejected", header: ("Отклонено заявок"), sort: "int", fillspace: true},
                        {
                            id: "totalMessages",
                            header: ("Всего сообщений от пользователя"),
                            sort: "int",
                            fillspace: true
                        },
                    ],
                    pager: 'groupPager',
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
                        onItemClick: function (id, e, node) {
                            if (id.column === "username" && node.innerText !== 'ВСЕГО') {
                                let index = 0;
                                let user = node.innerText;
                                let parentView1 = this.getParentView();
                                parentView1.hide();
                                this.getParentView().getParentView().addView({
                                    id: "button2",
                                    view: "button",
                                    label: "Назад",
                                    click: function () {
                                        let parentView2 = this.getParentView();
                                        parentView2.removeView("datatable3");
                                        parentView2.removeView("button2");
                                        parentView1.show();
                                    }
                                })
                                this.getParentView().getParentView().addView({
                                        id: "datatable3",
                                        view: "datatable",
                                        localId: "datatable3",
                                        url: "resource->http://localhost:9090/api/v1/report/chat/" + user + "/" + startDate + "/" + endDate,
                                        columns: [
                                            {id: "index", header: ("#"), sort: "int"},
                                            {id: "username", header: ("Имя пользователя"), sort: "string"},
                                            {id: "peer", header: "Кому отправлено", sort: "string", fillspace: true},
                                            {
                                                id: "message",
                                                header:[ {content:"textFilter", placeholder: "Сообщение"}],
                                                sort: "string",
                                                width: 180,
                                                fillspace: true
                                            },
                                            {id: "createdAt", header: "Отправлено", sort: "date", fillspace: true},
                                        ],
                                        fixedRowHeight: false,
                                        pager: "groupPager",
                                        on: {
                                            "data->onStoreUpdated": function () {
                                                this.data.each(function (obj, i) {
                                                    obj.index = i + 1;
                                                    index = obj.index;
                                                });

                                            },
                                            onAfterLoad: webix.once(function () {
                                                this.adjustRowHeight("message", true);
                                                this.refresh();
                                            })
                                        }
                                    },
                                );
                            }

                        }
                    }
                })
                _topReport.hide();
            }
        });
    }

}
