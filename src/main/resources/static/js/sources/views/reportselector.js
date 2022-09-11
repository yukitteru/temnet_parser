import {JetView} from "webix-jet";
import SearchingFlight from "jet-views/searchreport";


export default class ReportSelectorView extends JetView {
	config(){
		const _ = this.app.getService("locale")._;
		const theme = this.app.config.theme;

		return {
			width:400,
			multi:false,
			margin:1,
			css:theme,
			rows:[
				{
					header:_("Выборка"),
					body:SearchingFlight
				},
			],
		};
	}
}
