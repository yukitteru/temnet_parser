import {JetView} from "webix-jet";
import FlightSelectorView from "jet-views/reportselector";
import AllFlightsView from "jet-views/allreport";
import ReportSelectorView from "jet-views/reportselector";
import AllReportView from "jet-views/allreport";

export default class TopView extends JetView{
	config(){
		const _ = this.app.getService("locale")._;
		const theme = this.app.config.theme;

		return {
			rows:[
				{
					view:"toolbar",
					height:56,
					localId:"toolbar",
					css:theme,
					elements:[
						{
							paddingY:7,
							rows:[
								{
									margin:8,
									cols:[
										{ width:4 },
										{
											view:"label",
											label:"Temnet stat"
										},
										{},
										{
											view:"icon",
											icon:"mdi mdi-invert-colors",
											tooltip:_("Click to change the theme"),
											color:theme,
											click:function(){
												let color = this.config.color;
												color = !color ? "webix_dark" : "";
												try{
													webix.storage.local.put("theme_color",color);
												}
												catch(err){/* if cookies are disabled */}
												this.$scope.app.config.theme = color;
												this.$scope.app.refresh();
											}
										},
									]
								}
							]
						},
						{ width:6 }
					]
				},
				{
					type:"space",
					cols:[
						{
							rows:[
								ReportSelectorView,
								{}
							]
						},
						AllReportView
					]
				}
			]
		};
	}
	init(){

	}
}
