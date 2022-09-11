export function getDetailReport(grp, start, end) {
    return generateDetailReport(grp, start, end);
}


function generateDetailReport(grp, start, end) {
    return new webix.DataCollection({
        url: "resource->http://localhost:9090/api/v1/report/detail/" + grp + "/" + start + "/" + end,
    });
}