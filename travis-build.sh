#!/bin/bash

MERGE_PREFIX="Merge branch"
BRANCH_WHITELIST="^(develop|release/.*|hotfix/.*|bugfix/.*)$"
MAJOR_RELEASE_BRANCH="release/[0-9]+\.[xX]"
MINOR_RELEASE_BRANCH="release/[0-9]+\.[0-9]+\.[xX]"
PATCH_RELEASE_BRANCH="(hot|bug)fix/.*"
MAJOR_BRANCH_PATTERN="^${MAJOR_RELEASE_BRANCH}"
MINOR_BRANCH_PATTERN="^${MINOR_RELEASE_BRANCH}"
PATCH_BRANCH_PATTERN="^${PATCH_RELEASE_BRANCH}"
MAJOR_MESSAGE_PATTERN="^${MERGE_PREFIX} '${MAJOR_RELEASE_BRANCH}'"
MINOR_MESSAGE_PATTERN="^${MERGE_PREFIX} '${MINOR_RELEASE_BRANCH}'"
PATCH_MESSAGE_PATTERN="^${MERGE_PREFIX} '${PATCH_RELEASE_BRANCH}'"

build() {
    if [ "$TRAVIS_PULL_REQUEST" == "false" ]; then
        build_branch
    else
        build_default
    fi
}

build_branch() {
    if [ "$TRAVIS_BRANCH" == "master" ]; then
        ./gradlew $(scope_from_commit_message) final
    elif [[ $TRAVIS_BRANCH =~ $BRANCH_WHITELIST ]]; then
        ./gradlew $(scope_from_branch) snapshot
    else
        build_default
    fi
}

build_default() {
    ./gradlew build
}

scope_from_branch() {
    if [[ $TRAVIS_BRANCH =~ $MAJOR_BRANCH_PATTERN ]]; then
        echo -Prelease.scope=major
    elif [[ $TRAVIS_BRANCH =~ $MINOR_BRANCH_PATTERN ]]; then
        echo -Prelease.scope=minor
    elif [[ $TRAVIS_BRANCH =~ $PATCH_BRANCH_PATTERN ]]; then
        echo -Prelease.scope=patch
    fi
}

scope_from_commit_message() {
    if [[ $TRAVIS_COMMIT_MESSAGE =~ $MAJOR_MESSAGE_PATTERN ]]; then
        echo -Prelease.scope=major
    elif [[ $TRAVIS_COMMIT_MESSAGE =~ $MINOR_MESSAGE_PATTERN ]]; then
        echo -Prelease.scope=minor
    elif [[ $TRAVIS_COMMIT_MESSAGE =~ $PATCH_MESSAGE_PATTERN ]]; then
        echo -Prelease.scope=patch
    fi
}

build