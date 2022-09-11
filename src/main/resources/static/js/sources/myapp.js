import "./styles/app.css";
import {JetApp, HashRouter, plugins} from "webix-jet";

requirejs.config({
    baseUrl: 'js'
})
require(
    ['jet-views/allreport',
        'jet-views/reportselector',
        'jet-views/searchreport',
        'jet-views/reportinfo',
        'views/top',
        'util/resourceProxy'],
    function (main, resourceProxy) {
        webix.ready(function () {


        })
    });
export default class MyApp extends JetApp {
    constructor(config) {
        let theme = "";
        let cookies = true;
        try {
            theme = webix.storage.local.get("bank_app_theme");
        } catch (err) {
            cookies = false;
            webix.message("You disabled cookies. The language and theme won't be restored after page reloads.", "debug");
        }

        const defaults = {
            id: APPNAME,
            version: VERSION,
            router: HashRouter,
            debug: !PRODUCTION,
            start: "/top/reportinfo",
            theme: theme || ""
        };

        super({...defaults, ...config});

        let localeConfig = {
            webix: {

            }
        };
        if (cookies)
            localeConfig.storage = webix.storage.local;

        this.use(plugins.Locale, localeConfig);

        this.attachEvent("app:error:resolve", function (err, url) {
            webix.delay(() => this.show("/top/reportinfo"));
        });
    }
}

if (!BUILD_AS_MODULE) {
    webix.ready(() => {
        if (!webix.env.touch && webix.env.scrollSize && webix.CustomScroll)
            webix.CustomScroll.init();
        new MyApp().render();
    });
}

//track js errors
if (PRODUCTION) {
    window.Raven
        .config(
            "https://59d0634de9704b61ba83823ec3bf4787@sentry.webix.io/12"
        )
        .install();
}
