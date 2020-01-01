#!/bin/bash
changePath() {
    src=$1
    if [ -z "$src" ]; then
        return
    fi
    if [ ${src:1:1} = ':' ]; then
        src=/${src:0:1}/${src:3}
    fi
        dst=${src//\\/\/}
    echo "$dst"
}
cdWin() {
    cd "$(changePath "$1")"
}
