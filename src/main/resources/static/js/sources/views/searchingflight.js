import {JetView} from "webix-jet";
import {getCities} from "models/cities";
import {getTopReport} from "../models/topreport";
import {date_formatter} from "../util/dateformat";

export default class SearchingFlightView extends JetView {
    config() {
        const _ = this.app.getService("locale")._;
        const cities = getCities();

        return {
            view: "form",
            borderless: true,
            elementsConfig: {labelWidth: 100, labelAlign: "right"},
            elements: [
                {
                    id: "start",
                    view: "datepicker",
                    localId: "depart:date",
                    name: "departure_date",
                    label: _("Начало"),

                },
                {
                    id: "end",
                    view: "datepicker",
                    localId: "return:date",
                    name: "return_date",
                    label: _("Конец"),
                },
                {
                    view: "button",
                    type: "form",
                    value:_("Поиск"),
                    click: function () {
                        const data = this.getFormView().getValues();
                            let start = date_formatter(data.departure_date);
                            let end = date_formatter(data.return_date);
                            this.$scope.app.callEvent("search:flight",[start, end]);
                    }
                }
            ]
        };
    }
}
