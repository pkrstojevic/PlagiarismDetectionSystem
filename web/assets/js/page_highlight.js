jQuery(document).ready(function($) {
    //create searcher


    var searcher = "";
    var searcher2 = "";
    var text = "";

    $('.common-keywords').click(function() {
        $(".controls1").slideUp("fast", function() {
        });
        $(".controls2").slideUp("fast", function() {
        });
        $("#searchText").val($(this).text());
        $(".controls").slideDown("fast", function() {
            searcher = $("#highlight-plugin1").search({
                searchType: "highlightSelected",
                searchSelector: "span",
                scrollTo: true
            });            
            searcher2 = $("#highlight-plugin2").search({
                searchType: "highlightSelected",
                searchSelector: "span",
                scrollTo: true
            });
            text = $("#searchText").val();
        });
    });

    $('.keywords1').click(function() {
        $(".controls").slideUp("fast", function() {
        });
        $(".controls2").slideUp("fast", function() {
        });
        $("#searchText1").val($(this).text());
        $(".controls1").slideDown("fast", function() {
            searcher = $("#highlight-plugin1").search({
                searchType: "highlightSelected",
                searchSelector: "span",
                scrollTo: true
            });
            text = $("#searchText1").val();
        });
    });


    $('.keywords2').click(function() {
        $(".controls").slideUp("fast", function() {
        });        
        $(".controls1").slideUp("fast", function() {
        });
        $("#searchText2").val($(this).text());
        $(".controls2").slideDown("fast", function() {
            searcher = $("#highlight-plugin2").search({
                searchType: "highlightSelected",
                searchSelector: "span",
                scrollTo: true
            });
            text = $("#searchText2").val();
        });
    });

    //make sure we find the same text, otherwise clear search postions
    function find(up) {
        var currentText = searcher.getText();

        if (text != currentText) {
            var highlightText = text;
            searcher.setText(highlightText);
        }

        searcher.nextConcurrence(up);
    }


    function findBoth(up) {
        var currentText = searcher2.getText();

        if (text != currentText) {
            var highlightText = text;
            searcher2.setText(highlightText);
        }

        searcher2.nextConcurrence(up);
    }

    //bind events
    $("#prev1").click(function(e) {
        e.preventDefault();

        find(true);
    });

    $("#next1").click(function(e) {
        e.preventDefault();

        find(false);
    });

    //bind events
    $("#prev2").click(function(e) {
        e.preventDefault();

        find(true);
    });

    $("#next2").click(function(e) {
        e.preventDefault();

        find(false);
    });

    //bind events
    $("#prev").click(function(e) {
        e.preventDefault();
        findBoth(true);
        find(true);
    });

    $("#next").click(function(e) {
        e.preventDefault();
        findBoth(false);
        find(false);
    });
});
