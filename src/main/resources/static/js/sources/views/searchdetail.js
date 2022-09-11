import {JetView} from "webix-jet";
import {getDetailReport} from "../models/detailreport";

// noinspection JSUnresolvedFunction
export default class SearchDetailView extends JetView {
    config() {
        const _ = this.app.getService("locale")._;
        webix.extend(webix.ui.datatable, webix.ProgressBar);

        return {

            rows: [
                {
                    id: "datatable",
                    view: "datatable",
                    localId: "datatable",
                    url: "resource->http://localhost:9090/api/v1/report/detail/enso/2018-01-01%2000:00:00/2022-09-09%2000:00:00",
                    columns: [
                        {id: "index", header: _("#"), sort: "string", fillspace: true},
                        {id: "username", header: _("Имя пользователя"), sort: "string", fillspace: true},
                        {id: "finished", header: _("Закрыто заявок"), sort: "int", fillspace: true},
                        {id: "inProgress", header: _("Заявок взято в работу"), sort: "int", fillspace: true},
                        {id: "rejected", header: _("Отклонено заявок"), sort: "int", fillspace: true},
                        {
                            id: "totalMessages",
                            header: _("Всего сообщений от пользователя"),
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
                }

            ]
        };
    }

    init(view) {
        let grid = this.$$("datatable");
        this.on(this.app, "search:detail", (username, start, end) => {
            grid.hideOverlay();
            grid.clearAll();
            grid.define('url', "resource->http://localhost:9090/api/v1/report/detail/" + username + "/" + start + "/" + end);
        });
    }
}