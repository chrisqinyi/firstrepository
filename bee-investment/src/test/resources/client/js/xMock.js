Mock.mock(App.WS.buildURL("notice"), {
    notice : '@STRING(40)'
});

Mock.mock(App.WS.buildURL("save-notice"), {
    status : 1,
    notice : 'Response'
});

Mock.mock(App.WS.buildURL("delete-notice"), {
    status : 2
});

Mock.mock(App.WS.buildURL("update-notice"), {
    status : 3
});

Mock.mock(App.WS.buildURL("query-notice"), [ {
    notice : '111'
}, {
    notice : '222'
} ]);
