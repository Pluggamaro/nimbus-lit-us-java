#!/usr/bin/en bash
for file in README.md LICENSE .gitignore;do
    [ -f $file ] || { echo "Missing file $file"; exit 1;}
done
for pattern in ".env" "*.pem" "target/" "build/" "samconfig.toml";do
    grep -qxF "$pattern" .gitignore || { echo "Missing $pattern in .gitignore"; exit 1;}
done
echo OK
    

