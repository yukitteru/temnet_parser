define(function () {
    let ajax = webix.ajax().headers({
        'Content-type': 'application/json'
    })

    webix.proxy.resource = {
        init: function () {
            webix.extend(this, webix.proxy.rest)
        },
        load: function (view, params) {
            let args = ''

            args += '?page=' + (params ? params.start / view.config.datafetch : 0)
            args += '&size=' + view.config.datafetch

            let url = view.config.url.source

            return ajax.get(url + args)
                .then(function (value) {
                    let response = value.json()
                    return {
                        data: response.content,
                        pos: response.number * view.config.datafetch,
                        total_count: response.totalElements
                    }
                })
        },
    }
})
