const path = require('path');

const staticDir = path.resolve(__dirname, 'src/main/resources/static');
const generatedStaticDir = path.resolve(__dirname, 'target/generated-resources/static');

module.exports = {
    mode: 'production',
    entry: {
        main: path.resolve(staticDir, 'js/main.js'),
    },
    output: {
        path: path.resolve(generatedStaticDir, 'build/dist'),
        filename: '[name].bundle.js',
        clean: true,
        publicPath: '/build/dist/',
    },
    optimization: {
        splitChunks: { chunks: 'all' },
    },
};
