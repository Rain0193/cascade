var breadcrumb = new BreadCrumb('index');

var config = {
    mode: 'descriptive',
    orderThreshold: 3
};

renderDashboard();
renderTabPanels();
renderJourneyLists();
renderCoverageReport();

function reRenderLists(e){
    if (e.target.name === 'orderThreshold' && e.target.value.length == 0){
        config[e.target.name] = 3;
    } else if (e.target.name === 'orderThreshold' && (parseInt(e.target.value) > 9 || parseInt(e.target.value) < 1) ){
        config[e.target.name] = 3;
    } else {
        config[e.target.name] = e.target.value;
    }
    renderJourneyLists();
    return true;
}

function renderDashboard() {
    var successesCount = _.size(_.filter(directoryData.items, function (journey) {
        return journey.result === 'SUCCESS';
    }));
    var failureCount = _.size(_.filter(directoryData.items, function (journey) {
        return journey.result === 'FAILED';
    }));
    var errorCount = _.size(_.filter(directoryData.items, function (journey) {
        return journey.result === 'ERROR';
    }));
    var totalCount = _.size(directoryData.items);
    var percentage = totalCount > 0 ? Math.round(successesCount / totalCount * 100) : 0;

    var totalSeconds = Math.floor(directoryData.duration / 1000);
    var minutes = Math.floor(totalSeconds / 60);
    var remainingSeconds = totalSeconds - (minutes * 60);

    $('#index-percentage-label').html(percentage);
    $('#completeness-label').html(directoryData.completeness);

    $('#index-success-label').html(successesCount);
    $('#index-failure-label').html(failureCount);
    $('#index-error-label').html(errorCount);
    $('#index-minutes-label').html(minutes);
    $('#index-seconds-label').html(remainingSeconds);
}

function renderTabPanels() {
    var errorCount = _.size(_.filter(directoryData.items, function (journey) {
        return journey.result !== 'SUCCESS';
    }));

    var tabs = [];
    var active = true;
    if (errorCount > 0) {
        tabs.push({active: true, id: "failed-journeys", title: "Failures"});
        active = false;
    }
    tabs.push({active: active, id: "all-journeys", title: "All"});
    tabs.push({active: false, id: "coverage", title: "Coverage"});


    var tabHeadersTemplate = _.template($("#tab-headers-template").text());
    $("#tab-headers").append(tabHeadersTemplate({tabs: tabs}));

    var tabPanelsTemplate = _.template($("#tab-panels-template").text());
    $("#tab-panels").append(tabPanelsTemplate({tabs: tabs}));
}

function renderJourneyLists() {
    var journeysTemplate = _.template($("#journeys-template").text());

    var badJourneys = _.filter(directoryData.items, function (journey) {
        return journey.result !== 'SUCCESS';
    });

    $("#all-journeys #tab-body").html(journeysTemplate({journeys: directoryData.items, scenarioOrder: directoryData.scenarioOrder, config: config}));
    $("#failed-journeys #tab-body").html(journeysTemplate({journeys: badJourneys, scenarioOrder: directoryData.scenarioOrder, config: config}));

    $(".journey-list tr").hover(function() {
        $(this).addClass("text-primary");
    }, function(){
        $(this).removeClass("text-primary");
    });

    $(".journey-list tr").click(function() {
        breadcrumb.gotoNewLink("journey.html?journeyId=" + $(this).attr("data-journey-id"));
    });

}

function renderCoverageReport() {
    var coverageTemplate = _.template($("#coverage-template").text());


    _.each(directoryData.items, function (journey) {
        journey.states = _.uniq(_.flatMap(journey.scenarios, function (journeyScenario) {
            return _.map(_.filter(directoryData.scenarios, function (scenario) {
                return scenario.name === journeyScenario;
            }), function (scenario) {
                return scenario.state;
            });
        }));
    });


    var stateHistogram = {};
    _.each(directoryData.states, function (state) {
        var count = 0;
        _.each(directoryData.items, function (journey) {
            if (_.some(journey.states, function (s) {
                    return s === state.name;
                })) {
                count++;
            }
        });
        stateHistogram[state.name] = count;
    });


    var scenarioHistogram = {};
    _.each(directoryData.scenarios, function (scenario) {
        var count = 0;
        _.each(directoryData.items, function (journey) {
            if (_.some(journey.directoryData, function (s) {
                    return s === scenario.name;
                })) {
                count++;
            }
        });
        scenarioHistogram[scenario.name] = count;
    });

    $("#coverage #tab-body").html(coverageTemplate({"stateData": stateHistogram, "scenarioData": scenarioHistogram}));
}