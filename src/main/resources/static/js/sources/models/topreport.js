export function getTopReport(start, end) {
    return generateTopReport(start, end);
}


function generateTopReport(start, end) {
    return new webix.DataCollection({
        url: "resource->http://localhost:9090/api/v1/report/top/" + start + "/" + end,
    });
}
