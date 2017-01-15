<?php

require_once __DIR__ . '/vendor/autoload.php';

class Handler
{
    /**
     * @var \Illuminate\Filesystem\Filesystem
     */
    protected $files;

    /**
     * @var string
     */
    protected $docs = __DIR__ . '\\react\\docs\\docs';

    /**
     * @var string
     */
    protected $contributing = __DIR__ . '\\react\\docs\\contributing';

    /**
     * @return void
     */
    public function __construct()
    {
        $this->files = new \Illuminate\Filesystem\Filesystem();
    }

    /**
     * @return void
     */
    public function handle()
    {
        $this->git()
            ->pull();

        $ruler = $this->ruler();
		
		foreach ([$this->docs, $this->contributing] as $directory) {
			/** @var SplFileInfo $fileInfo */
			foreach ($this->files->allFiles($directory) as $fileInfo) {
				$getContents = $this->files->get($fileInfo->getRealPath());
				
				if (preg_match('/---(.*)---/s', $getContents, $head)) {
					$title = '';
					$prev = '';
					$next = '';
					if (preg_match_all('/(.+?): (.*)/', $head[1], $atts)) {
							for ($i = 0; $i < count($atts[0]); $i++) {
								$name = $atts[1][$i];
								$value = $atts[2][$i];
								switch ($name) {
									case "title":
										$title = $value;
										break;
									case "prev":
										$prev = $value;
										break;
									case "next":
										$next = $value;
										break;
								}
							}
					}
					
					$getContents = str_replace($head[0], '# ' . $title, $getContents);
				}
				
				$getContents = preg_replace('/js{.+?}/', 'js', $getContents);
				$getContents = preg_replace('/javascript{.+?}/', 'js', $getContents);
				
				$getContents = $this->markdown($getContents);

				$getContents = str_replace('<pre>', '<div class="highlight"><pre>', $getContents);
				$getContents = str_replace('</pre>', '</pre></div>', $getContents);

				if (preg_match_all('/href="(.+?)"/', $getContents, $matches)) {
					for ($i = 0; $i < count($matches[0]); $i++) {
						$value = $matches[1][$i];
						if ( ! starts_with($value, $ruler)) {
							$relative = $this->replacer($value);
							$getContents = str_replace('href="' . $value . '"', 'href="' . $relative . '"', $getContents);
						}
					}
				}

				if (preg_match_all('/src="(.+?)"/', $getContents, $matches)) {
					for ($i = 0; $i < count($matches[0]); $i++) {
						$value = $matches[1][$i];
						if ( ! starts_with($value, $ruler)) {
							$relative = $this->replacer($value);
							$getContents = str_replace('src="' . $value . '"', 'src="' . $relative . '"', $getContents);
						}
					}
				}
				
				$getBasename = $fileInfo->getBasename('.md');
				$this->files->put(__DIR__ . '/app/src/main/assets/' . $getBasename . '.html', $getContents);
			}
		}
	}

    /**
     * @return array
     */
    protected function ruler()
    {
        return [
            'file:///android_asset/',
            '//',
            'http://',
            'https://',
            '_',
            '#',
        ];
    }

    /**
     * @return $this
     */
    protected function git()
    {
        if ( ! $this->files->isDirectory(__DIR__ . '/react')) {
            exec('git clone https://github.com/facebook/react.git ' . __DIR__ . '/react', $output, $returnCode);
        }

        return $this;
    }

    /**
     * @return $this
     */
    protected function pull()
    {
        exec('cd ' . $this->docs . ' && git pull');

        return $this;
    }

    /**
     * @param $value
     * @param SplFileInfo $fileInfo
     * @return string
     */
    protected function replacer($value)
    {
        $android_asset = $value;
        if (preg_match('/(.*)#/', $value, $matches)) {
            $android_asset = $matches[1];
        }

        if (starts_with($android_asset, "/react/docs/")) {
            $specified = $android_asset;
            if ( ! pathinfo($specified, PATHINFO_EXTENSION)) {
                $specified .= '.html';
            }

            $absolute = str_replace_first("/react/docs/", 'file:///android_asset/', $specified);
            $relative = str_replace(DIRECTORY_SEPARATOR, '/', $absolute);

            return $relative;
        }

        if (starts_with($android_asset, "/react/img/")) {
            $absolute = str_replace_first("/react/img/", 'file:///android_asset/img/', $android_asset);
            $relative = str_replace(DIRECTORY_SEPARATOR, '/', $absolute);

            return $relative;
        }

        return $value;
    }

    /**
     * @param string $version
     * @param string $getContents
     * @return string
     */
    protected function replaceLinks($version, $getContents)
    {
        return str_replace("{{version}}", $version, $getContents);
    }

    /**
     * @param string $getContents
     * @return string
     */
    protected function markdown($getContents)
    {
        return (new ParsedownExtra)->text($getContents);
    }
}

(new Handler)->handle();
