import systems.danger.kotlin.*
import systems.danger.kotlin.models.github.*

danger(args) {
    onGitHub {
        messageDiff()
        warnWorkInProgress()
        message("Good job @${pullRequest.user.login}!")
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
