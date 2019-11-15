#!/bin/bash
. cdWin.sh
testChgPath(){
    dst=$(changePath 'a/b/c')
    assertEquals 'a/b/c' "$dst"
    dst=$(changePath 'f:\backup')
    assertEquals '/f/backup' "$dst"
    
    assertEquals '' $(changePath)
}


. ../shunit/shunit2
