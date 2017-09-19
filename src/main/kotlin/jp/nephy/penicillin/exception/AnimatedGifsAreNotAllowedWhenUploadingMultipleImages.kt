package jp.nephy.penicillin.exception

class AnimatedGifsAreNotAllowedWhenUploadingMultipleImages: AbsTwitterErrorMessage(323, "Animated GIFs are not allowed when uploading multiple images.", "Corresponds with HTTP 400. Only one animated GIF is allowed to be attached to a single Tweet.")