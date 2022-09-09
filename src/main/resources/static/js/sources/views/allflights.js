import {JetView, plugins} from "webix-jet";

export default class AllFlightsView extends JetView {
	config(){
		const _ = this.app.getService("locale")._;
		const theme = this.app.config.theme;

		return {
			gravity:3,
			rows:[
				{
					view:"toolbar",
					localId:"toolbar",
					css:theme,
					cols:[
						{ width:4 },
						{ view:"label", label:_("") },
						{},

						{ width:6 }
					]
				},
				{ $subview:true }
			]
		};
	}

}
