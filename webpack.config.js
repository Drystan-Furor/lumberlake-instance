const path = require('path');

const staticDir = path.resolve(__dirname, 'src/main/resources/static');

module.exports = {
    mode: 'production',
    entry: {
        main: path.resolve(staticDir, 'js/main.js'),
        bucket: path.resolve(staticDir, 'js/bucket.js'),
        achievements: path.resolve(staticDir, 'js/achievements.js'),
    },
    output: {
        path: path.resolve(staticDir, 'build/dist'),
        filename: '[name].bundle.js',
        clean: true,
        publicPath: '/build/dist/',
    },
    optimization: {
        splitChunks: { chunks: 'all' },
    },
};
