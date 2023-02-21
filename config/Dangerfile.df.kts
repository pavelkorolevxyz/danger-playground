import systems.danger.kotlin.*
import systems.danger.kotlin.models.git.Git
import systems.danger.kotlin.models.github.GitHub

danger(args) {
    onGitHub {
        messageDiff()
        warnWorkInProgress()
        message("Good job @${pullRequest.user.login}!")
    }
    onGit {
        failIfDangerWordAdded()
    }
}

fun GitHub.warnWorkInProgress() {
    if ("WIP" in pullRequest.title) {
        warn(
            ":construction: PR is marked with Work in Progress (WIP)",
        )
    }
}

fun GitHub.messageDiff() {
    message(
        "This PR has ${pullRequest.additions} additions and ${pullRequest.deletions} deletions",
    )
}

fun GitHub.failBigPR() {
    val additions = pullRequest.additions ?: 0
    val deletions = pullRequest.deletions ?: 0
    if (additions + deletions > 300) {
        fail(
            "This PR is too big, try to split it into smaller PRs",
        )
    }
}

fun Git.failIfDangerWordAdded() {
    val files = createdFiles + modifiedFiles
    for (file in files) {
        for ((index, line) in file.lineSequence().withIndex()) {
            if (line.contains("danger", ignoreCase = true)) {
                fail(
                    message = "Danger word must not be used. Use issue instead",
                    file = file,
                    line = index + 1,
                )
            }
        }
    }
}