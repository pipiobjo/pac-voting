const webpackMerge = require('webpack-merge');
const DefinePlugin = require('webpack/lib/DefinePlugin');

const {ENV, validateConfig, baseWebpackConfig, absolutePath} = require('./webpack.commons.js');

var environment = process.env.NODE_ENV = process.env.ENV = ENV.TEST;

module.exports = validateConfig(webpackMerge(baseWebpackConfig, {
  devtool: 'inline-source-map',
  stats: {
    colors: true,
    reasons: true
  },
  metadata: {
    ENV: environment
  },
  module: {
    loaders: [
      {
        test: /main\.scss$/,
        loader: 'style!css!sass'
      },
      {
        test: /\.scss$/,
        loader: 'raw!sass',
        exclude: [
          absolutePath('../src/main/frontend/scss/main.scss')
        ]
      }
    ],
    postLoaders: [
      // instrument only testing sources with Istanbul
      {
        test: /\.(js|ts)$/,
        include: absolutePath('../src/main/frontend'),
        loader: 'istanbul-instrumenter-loader',
        exclude: [
          /\.(e2e|spec)\.ts$/,
          /node_modules/
        ]
      }
    ]
  },
  plugins: [
    new DefinePlugin({
      'ENV': JSON.stringify(environment),
      'HMR': (ENV.DEV === environment)
    })
  ]
}));
