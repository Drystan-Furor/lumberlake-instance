// webpack.config.js
const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CopyWebpackPlugin = require('copy-webpack-plugin');

const dirEntry = './src/js/';

module.exports = {
    mode: 'production',
    entry: {
        main: dirEntry + 'main.js',
        bucket: dirEntry + 'bucket.js',
        achievements: dirEntry + 'achievements.js',
    },
    output: {
        path: path.resolve(__dirname, 'build/dist'),   // <- Publish Directory op Render
        filename: '[name].bundle.js',
        clean: true,                                   // schoon build-map op
        publicPath: '/',                               // houd root-relatieve paden netjes
    },
    optimization: {
        splitChunks: { chunks: 'all' },
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: './src/index.html',                // maakt build/dist/index.html
            inject: 'body',
        }),
        new CopyWebpackPlugin({
            patterns: [
                { from: 'src/img', to: 'img', noErrorOnMissing: true },
                { from: 'src/css', to: 'css', noErrorOnMissing: true },
                { from: 'src/pages', to: 'pages', noErrorOnMissing: true },
                { from: 'src/robots.txt', to: '.', noErrorOnMissing: true },
                { from: 'src/assets/favicon', to: '.', noErrorOnMissing: true },
                { from: 'src/sitemap.xml', to: '.', noErrorOnMissing: true },
                { from: 'src/assets/sitemaps', to: 'sitemaps', noErrorOnMissing: true },
                { from: 'src/assets/llms.txt', to: '.', noErrorOnMissing: true },
            ],
        }),

    ],
    module: {
        rules: [
            // alleen als je CSS wilt bundelen i.p.v. kopiëren:
            // { test: /\.css$/, use: ['style-loader', 'css-loader'] },
            { test: /\.(png|jpe?g|gif|svg)$/i, type: 'asset' },
        ],
    },
};
