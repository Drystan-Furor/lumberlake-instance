# Arts ICT
## landingpage for generic purposes

"Run" scripts work after initial setup
``` run tailwind
npm run tailwind
```
``` run build
npm run build
```
``` run prettier
npm run prettier
```

for f in *.png; do avifenc -q 50 -s 2 "$f" "${f%.*}.avif"; done
---
### initial setup

Step 1
```install lts version
nvm install 20
nvm use 20
```

```init npm
npm init 
```

```install npm
npm install 
```

```install tailwind css
npm install -D tailwindcss
```

``` install sharp
npm install sharp
```

``` init tailwind
npx tailwindcss init
```

Step 2
``` install webpack
rm -rf node_modules
rm package-lock.json

npm install --save-dev webpack webpack-cli
npm i -D webpack webpack-cli html-webpack-plugin copy-webpack-plugin

```

``` Using npm scripts and husky for Git Hooks
npm install husky --save-dev
```

Step 3
``` Add a scripts field to your package.json to run the commands:  
"scripts": {
    "tailwind": "npx tailwindcss -i ./src/css/input.css -o ./src/css/style.css --watch",
    "prettier": "npx prettier --write **/*.html",
    "build": "npx webpack --config webpack.config.js --watch"
  },

```

``` Add a husky field to your package.json to run the webpack command before every push:
{
  "husky": {
    "hooks": {
      "pre-push": "npx webpack --config webpack.config.js"
    }
  }
}

```


gh repo create lumberlake-instance --public --source=. --remote=origin --push

Step 4
``` Start the Tailwind CLI build process
npx tailwindcss -i ./src/input.css -o ./src/style.css --watch
```
``` run webpack
npx webpack --config webpack.config.js --watch
```
---

# GIT commands
``` git # This will discard all unstaged changes
git reset --hard HEAD

```

``` git
git rebase origin/branch
git rebase origin/feature/branchname
```
---

# sitemap

```
npm i sitemap

```

``` Once you've confirmed that listofurls.txt exists in your current directory
 npx sitemap --gzip --index --index-base-url https://example.com/path/to/sitemaps/ < listofurls.txt > sitemap-index.xml.gz
```

npx sitemap --gzip --index --index-base-url https://lumberlake.nl/sitemaps/ < listofurls.txt > sitemap-index.xml.gz


---

# Robots
## Create the robots.txt File
create a robots.txt file in the root directory of your project. 
This is the same directory where your website’s index.html file is located.
```robots.txt
User-agent: *
Disallow:

Sitemap: http://www.yourdomain.com/sitemaps/sitemap.xml

```
---
#!/bin/bash

# Set the initial filename counter.
counter=22

# Specify the directory containing the images.
# Replace "/path/to/images" with the actual path to your folder.
# directory="/path/to/images"
directory="/Users/tristan/Workspaces/Developer/demo-images/test"


# Loop through each JPEG file in the directory.
for file in "$directory"/*.jpg; do
# Construct the new filename.
newfile=$(printf "%s/%d.jpg" "$directory" "$counter")

# Rename the file.
mv "$file" "$newfile"

# Increment the counter.
((counter++))
done

echo "Renaming complete."


## GIT
To create a new repo from a new folder:

mkdir my-repo
cd my-repo
git init
echo "# my-repo" > README.md
git add .
git commit -m "Initial commit"
gh repo create my-repo --public --source=. --remote=origin --push

For a private repo, replace --public with --private.

If you already have a local project folder:

cd your-project
git init
git add .
git commit -m "Initial commit"
gh repo create your-project --public --source=. --remote=origin --push

If you want the branch to be master instead of main, run this before gh repo create:

git branch -M master

Your repo is using HTTPS, and the stored GitHub credential is invalid. GitHub passwords do not work for git push; they were removed for Git operations on August 13, 2021.

Fastest fix on this machine:

gh auth logout -h github.com -u Drystan-Furor
gh auth login -h github.com --git-protocol https --web
gh auth setup-git
git push -u origin master

What this does:

- gh auth login gets you a fresh GitHub token
- gh auth setup-git teaches Git to use that token for HTTPS pushes

If you prefer SSH instead, I can give you the SSH setup commands instead.
