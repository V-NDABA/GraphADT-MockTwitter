function login() {
    var username = document.getElementsByName("username");
    var logged = main.login(username);
    if (logged == true) {

    }
}

//Method to follow a user
function FollowUser() {
    var userName = document.getElementById("userName");
    main.followMethod(userName);
};

function checkInterface() {
    var unfollowed = main.unfollowedProfiles();
    for (let users of unfollowed) {
        users.toString();
        document.getElementsByClassName("who-to-follow__block").innerHTML = users;
    }
    var tweets = main.showTweets();
    for (let tweet of tweets) {
        tweet.toString();
        document.getElementById("feed").innerHTML = tweet;
    }
};

function whoToFollow() {
    result: main.unfollowedProfiles();
};

function getJsConnector() {
    return jsConnector;
};


/*function feed_tweets() {
    //feed_tweets: function (feeds){
    for (let tweets of feeds) {
        tweets.toString();
        document.getElementById("feed").innerHTML(tweets);
    }

};

function getFeed() {
    return feed;
};
function feed_tweets() {
    for (let tweets of jsConnector) {
        tweets.toString();
        document.getElementById(feed).innerHTML(tweets);

    }
}*/