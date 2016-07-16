var path = require('path');
var autoprefixer = require('autoprefixer');
var validateWebpackConfig = require('webpack-validator');
var Joi = require('joi');

// Environments
const ENV = {
  DEV: 'development',
  PROD: 'production',
  TEST: 'test'
};

var absolutePath = function (args) {
  args = Array.prototype.slice.call(arguments, 0);
  return path.join.apply(path, [__dirname].concat(args));
};

function validateConfig(config) {
  var customSchema = Joi.object({
    // this would just allow the property and doesn't perform any additional validation
    sassLoader: Joi.any(),
    htmlLoader: Joi.any()
  });

  return validateWebpackConfig(config, {schemaExtension: customSchema});
}

// Base configuration
var baseWebpackConfig = {

  // static data for index.html
  metadata: {
    baseUrl: '/'
  },

  resolve: {
    extensions: ['', '.ts', '.js'],
    cache: true,
    root: absolutePath('../src/main/frontend')
  },

  cache: true,
  debug: false,
  
  tslint: {
    emitErrors: false,
    failOnHint: false,
    resourcePath: './src/main/frontend'
  },

  module: {
    preLoaders: [
      {
        test: /\.ts$/,
        loader: 'tslint-loader',
        exclude: [
          absolutePath('../node_modules')
        ]
      },
      {
        test: /\.js$/,
        loader: "source-map-loader",
        exclude: [
          absolutePath('../node_modules/rxjs'),
          absolutePath('../node_modules/@angular'),
          absolutePath('../node_modules/@angular2-material'),
          absolutePath('../node_modules/ng2-webstorage')
        ]
      }
    ],
    loaders: [
      {
        test: /\.ts$/,
        loader: 'ts'
      },

      {
        test: /\.json$/,
        loader: 'json'
      },

      {
        test: /\.css$/,
        loader: 'raw'
      },

      {
        test: /\.html$/,
        loader: 'raw',
        exclude: [
          absolutePath('../src/main/frontend/index.html')
        ]
      },

      {
        test: /\.(png|jpg|gif|svg)/,
        loader: 'url',
        query: {
          limit: 10000,
          name: '[name].[ext]?[hash]'
        }
      },

      {
        test: /\.(woff|eot|ttf|woff(2)?|otf)/i,
        loader: 'file-loader?[name].[ext]?[hash]'
      }

    ]
  },
  sassLoader: {
    includePaths: [
      './node_modules/font-awesome/scss',
      './node_modules/normalize.css'
    ]
  },
  postcss: [
    autoprefixer({browsers: ['last 2 versions'], remove: false})
  ],
  plugins: []
};

module.exports = {
  absolutePath: absolutePath,
  ENV: ENV,
  baseWebpackConfig: baseWebpackConfig,
  validateConfig: validateConfig
};
