import {JetView, plugins} from "webix-jet";
import ReportInfoView from "jet-views/reportinfo";


export default class AllReportView extends JetView {
    config() {
        const _ = this.app.getService("locale")._;
        const theme = this.app.config.theme;

        return {
            rows: [
                {
                    view: "toolbar",
                    localId: "toolbar",
                    css: theme,
                    cols: [
                        {width: 4},
                        {view: "label", label: _("")},
                        {},
                        {
                            view: "pager",
                            id: 'groupPager',
                            size: 50,
                            group: 5,
                            template: "{common.first()}{common.prev()}{common.pages()}{common.next()}{common.last()}"
                        },
                        {width: 6},
                        {
                            view: "icon",
                            id: "excel",
                            icon: "mdi mdi-file-excel",
                            tooltip: _("Нажмите чтобы скачать отчет Excel"),
                            click: function () {
                                let map = this.getTopParentView().getTopParentView().queryView({view: "datatable"}, "all").map(view => view);
                                if(map.length > 1) {
                                    webix.toExcel(
                                        [map[0], map[1]],
                                        {filename: "report" + new Date().toISOString().slice(0, 10)}
                                    );
                                }else if(map.length > 2) {
                                    webix.toExcel((
                                        [map[0], map[1], map[2]],
                                            {filename: "report" + new Date().toISOString().slice(0, 10)}
                                    ))
                                } else {
                                    webix.toExcel(
                                        [map[0]],
                                        {filename: "report" + new Date().toISOString().slice(0, 10)}
                                    );
                                }
                            }
                        },
                    ]
                },
                {$subview: true},
            ]
        };
    }

}
