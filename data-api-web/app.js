var path = require('path');
var express = require('express');
var app = express();
var PORT = 8081;
var redis = require('redis');
var requestProxy = require('express-request-proxy');

require('redis-streams')(redis);

app.use(express.static(path.join(__dirname, 'src')));

app.get('/', function (request, response) {
    response.sendFile(__dirname + '/src/index.html')
});

app.all('/api/*', requestProxy({
    url: 'http://localhost:1378/api/*'
}));

app.listen(PORT, function (error) {
    if (error) {
        console.error(error);
    } else {
        console.info("==> 🌎  Listening on port %s. Visit http://localhost:%s/ in your browser.", PORT, PORT);
    }
});