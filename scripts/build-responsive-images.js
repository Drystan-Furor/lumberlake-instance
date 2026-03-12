const fs = require('fs/promises');
const path = require('path');
const sharp = require('sharp');

const projectRoot = path.resolve(__dirname, '..');
const sourceRoot = path.join(projectRoot, 'src/main/resources/static/img');
const targetRoot = path.join(projectRoot, 'target/generated-resources/static/img');

const variants = [
    {
        input: path.join(sourceRoot, 'lumberlake-banner.avif'),
        output: path.join(targetRoot, 'lumberlake-banner-640.avif'),
        width: 640,
        quality: 45,
    },
    {
        input: path.join(sourceRoot, 'lumberlake-banner.avif'),
        output: path.join(targetRoot, 'lumberlake-banner-960.avif'),
        width: 960,
        quality: 45,
    },
    {
        input: path.join(sourceRoot, 'lumberlake-banner.avif'),
        output: path.join(targetRoot, 'lumberlake-banner-1600.avif'),
        width: 1600,
        quality: 45,
    },
    {
        input: path.join(sourceRoot, 'sociaal.avif'),
        output: path.join(targetRoot, 'sociaal-640.avif'),
        width: 640,
        quality: 45,
    },
];

async function buildVariant({ input, output, width, quality }) {
    await fs.mkdir(path.dirname(output), { recursive: true });

    let pipeline = sharp(input).rotate();
    if (width) {
        pipeline = pipeline.resize({ width, withoutEnlargement: true });
    }

    await pipeline.avif({ quality }).toFile(output);
    console.log(`Built ${path.relative(projectRoot, output)}`);
}

Promise.all(variants.map(buildVariant)).catch((error) => {
    console.error(error);
    process.exit(1);
});
