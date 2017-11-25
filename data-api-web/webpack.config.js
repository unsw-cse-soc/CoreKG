var debug = process.env.NODE_ENV !== "production";
var webpack = require('webpack');
var path = require('path');

module.exports = {
    context: path.join(__dirname, "src"),
    devtool: debug ? "inline-sourcemap" : null,
    entry: ['babel-polyfill', './js/client.js'],
    module: {
        loaders: [
            {
                test: /\.jsx?$/,
                exclude: /(node_modules|bower_components)/,
                loader: 'babel-loader',
                query: {
                    presets: ['react', 'es2015', 'stage-0'],
                    plugins: ['react-html-attrs', 'transform-class-properties', 'transform-decorators-legacy'],
                }
            },
            {
                test: /\.css$/,
                loader: 'style-loader!css-loader'
            },
            {
                test: /\.woff(\?v=\d+\.\d+\.\d+)?$/,
                loader: "url?limit=10000&mimetype=application/font-woff&name=./[hash].[ext]"
            }, {
                test: /\.woff2(\?v=\d+\.\d+\.\d+)?$/,
                loader: "url?limit=10000&mimetype=application/font-woff&name=./[hash].[ext]"
            }, {
                test: /\.ttf(\?v=\d+\.\d+\.\d+)?$/,
                loader: "url?limit=10000&mimetype=application/octet-stream"
            }, {
                test: /\.eot(\?v=\d+\.\d+\.\d+)?$/,
                loader: "file"
            }, {
                test: /\.svg(\?v=\d+\.\d+\.\d+)?$/,
                loader: "url?limit=10000&mimetype=image/svg+xml"
            }, {
                test: /\.png(\?v=\d+\.\d+\.\d+)?$/,
                loader: "url?limit=10000&mimetype=image/png"
            }, {
                test: /\.jpg(\?v=\d+\.\d+\.\d+)?$/,
                loader: "url?limit=10000&mimetype=image/jpg"
            }
        ]
    },
    resolve: {
        alias: {
            'jquery-ui-widgets': 'jquery-ui/ui/widgets',
            'jquery-ui': 'jquery-ui/ui',
            'jquery-ui-css': 'jquery-ui/../../themes/base',
            'jquery': path.join(__dirname, 'node_modules/jquery/dist/jquery')
        },
        extensions: ['', '.js', '.jsx']
    },
    output: {
        path: __dirname + "/src/",
        filename: "client.min.js",
        publicPath: "http://localhost:8081/",
    },
    plugins: debug ? [new webpack.ProvidePlugin({
        $: "jquery",
        jQuery: "jquery",
        "window.jQuery": "jquery"
    })] : [
            new webpack.ProvidePlugin({
                $: "jquery",
                jQuery: "jquery",
                "window.jQuery": "jquery"
            }),
            new webpack.DefinePlugin({
                "process.env": {
                    NODE_ENV: JSON.stringify("production")
                }
            }),
            new webpack.optimize.DedupePlugin(),
            new webpack.optimize.OccurenceOrderPlugin(),
            new webpack.optimize.UglifyJsPlugin({ mangle: false, sourcemap: false }),
        ],
    devServer: {
        port: 8081,
        historyApiFallback: true,
        proxy: {
            '/api/curation/*': {
                target: 'http://localhost:8082',
                changeOrigin: true
            },
            '/api/*': {
                target: 'http://localhost:8080',
                changeOrigin: true
            }
        }
    }
};