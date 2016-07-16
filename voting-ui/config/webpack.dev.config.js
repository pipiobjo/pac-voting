const fs = require('fs');

const webpackMerge = require('webpack-merge');
const DefinePlugin = require('webpack/lib/DefinePlugin');
const webpack = require('webpack');
const CommonsChunkPlugin = require('webpack/lib/optimize/CommonsChunkPlugin');
const CopyWebpackPlugin = require('copy-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');

const {ENV, validateConfig, baseWebpackConfig, absolutePath} = require('./webpack.commons.js');


var environment = process.env.NODE_ENV = process.env.ENV = ENV.DEV;

var inlinedCss = fs.readFileSync('./src/main/frontend/css/inline.css', {encoding: 'utf8'});

module.exports = validateConfig(webpackMerge(baseWebpackConfig, {
  devtool: 'cheap-module-eval-source-map',
  metadata: {
    ENV: environment,
    host: 'localhost',
    port: 3000
  },
  entry: {
    'polyfills': './src/main/frontend/polyfills.ts',
    'vendor': './src/main/frontend/vendor.ts',
    'main': './src/main/frontend/main.ts'
  },
  output: {
    path: absolutePath('../src/main/resources/static/'),
    filename: '[name].bundle.js',
    sourceMapFilename: '[name].map',
    chunkFilename: '[id].chunk.js',
    publicPath: 'http://localhost:3000/'
  },
  // Webpack Development Server config
  devServer: {
    port: 3000,
    host: 'localhost',
    contentBase: './src/main/frontend',
    outputPath: './tmp/',
    historyApiFallback: true,
    watchOptions: {
      aggregateTimeout: 300,
      poll: 1000
    },
    quiet: false,
    noInfo: true,
    proxy: {
      '/api/*': 'http://localhost:8080',
      '/auth/*': 'http://localhost:8080'
    }
  },
  module: {
    loaders: [
      // Saas config
      {
        test: /main\.scss$/,
        loader: 'style!css?sourceMap!postcss!sass?sourceMap'
      },
      {
        test: /\.scss$/,
        loader: 'raw!postcss!sass',
        exclude: [
          absolutePath('../src/main/frontend/scss/main.scss')
        ]
      }
    ]
  },
  tslint: {
    emitErrors: false,
    failOnHint: false
  },
  plugins: [
    new DefinePlugin({
      'ENV': JSON.stringify(environment),
      'HMR': (ENV.DEV === environment)
    }),
    new webpack.optimize.OccurenceOrderPlugin(true),
    new CommonsChunkPlugin({
      name: ['vendor', 'polyfills'],
      minChunks: Infinity
    }),
    new CopyWebpackPlugin([
      {
        from: './src/main/frontend/assets',
        to: 'assets'
      }
    ]),
    new HtmlWebpackPlugin({
      template: './src/main/frontend/index.html',
      chunksSortMode: 'dependency',
      minify: false,
      inlineCss: '<style>' + inlinedCss + '</style>'
    })
  ]
}));
