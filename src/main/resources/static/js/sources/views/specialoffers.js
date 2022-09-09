import {JetView} from "webix-jet";
import {getOffers} from "models/offers";
import {getTopReport} from "../models/topreport";

export default class SpecialOffersView extends JetView {
    config() {
        const _ = this.app.getService("locale")._;
        let topReport = getTopReport("2018-01-01%2000:00:00", "2022-01-31%2000:00:00");

        return {
            rows: [
                {
                    view: "datatable",
                    localId: "datatable",
                    data: topReport,
                    onClick: {
                        "groupName": function (event, column, target) {
                            webix.message("Click on header");
                        }
                    },
                    select: true,
                    columns: [
                        {id: "index", header: "#", sort: "int", fillspace: true},
                        {id: "groupName", header: _("Имя группы"), sort: "string", fillspace: true},
                        {id: "activeUsers", header: _("Активные"), sort: "int", fillspace: true},
                        {id: "numberOfAccounts", header: _("Уч. записей Miranda"), sort: "int", fillspace: true},
                        {id: "messageCount", header: _("Кол-во сообщений"), sort: "int", fillspace: true},
                    ],
                    on: {
                        "data->onStoreUpdated": function () {
                            this.data.each(function (obj, i) {
                                obj.index = i + 1;
                            });
                        }
                    },
                }

            ]
        };
    }

    init() {
        const grid = this.$$("datatable");
        this.on(this.app, "search:flight", (start, end) => {
            grid.hideOverlay();
            webix.message("Данные загружаются. Ожидайте.")
            let topReport = getTopReport(start, end)
            grid.clearAll();
            grid.parse(topReport);
        });

    }
}
