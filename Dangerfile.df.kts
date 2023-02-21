import systems.danger.kotlin.*
import systems.danger.kotlin.models.github.*

danger(args) {
    onGitHub {
        warnWorkInProgress()
    }
}

fun GitHub.warnWorkInProgress() {
    if ("WIP" in pullRequest.title) {
        warn(
            ":construction: PR is marked with Work in Progress (WIP)",
        )
    }
}
