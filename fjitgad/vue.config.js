const webpack = require('webpack');
publicPath: process.env.NODE_ENV === 'production';
module.exports = {
    publicPath: process.env.NODE_ENV === 'production'? '/jit/': '/',
    configureWebpack: {
        plugins: [
            new webpack.ProvidePlugin({
                $: 'jquery',
                jquery: 'jquery',
                'window.jQuery': 'jquery',
                jQuery: 'jquery'
            })
        ]
    },
    devServer: {
        port: 9000, // CHANGE YOUR PORT HERE!
      },
}