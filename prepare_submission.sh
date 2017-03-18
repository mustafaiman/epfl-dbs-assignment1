#!/usr/bin/env bash
GASPAR="iman"
SUBMISSION_DIR="submission/$GASPAR/exercise1"
SOURCE_BASE="src/main/java"

rm -rf $SUBMISSION_DIR
mkdir -p $SUBMISSION_DIR/task1
mkdir -p $SUBMISSION_DIR/task2
mkdir -p $SUBMISSION_DIR/task3

FILES_T1=($SOURCE_BASE/JumpingWindow.java $SOURCE_BASE/Window.java $SOURCE_BASE/BloomFilter.java $SOURCE_BASE/task1.java out1.txt questions1.pdf)
FILES_T2=($SOURCE_BASE/task2.java $SOURCE_BASE/InsufficientMemoryException.java $SOURCE_BASE/betterFrequencyEstimator.java $SOURCE_BASE/CMSketch.java $SOURCE_BASE/BloomFilter.java out2.txt)
FILES_T3=($SOURCE_BASE/task3.java $SOURCE_BASE/rangeBF.java $SOURCE_BASE/BloomFilter.java out3.txt)

copy_files() {
    par_name=$1[@]
    task_f=$2
    FILES=("${!par_name}")
    for file in "${FILES[@]}"; do
        cp $file $SUBMISSION_DIR/$task_f/.
        if [ $? -ne 0 ]
        then
            exit 1
        fi
    done;
}

copy_files FILES_T1 task1
copy_files FILES_T2 task2
copy_files FILES_T3 task3

