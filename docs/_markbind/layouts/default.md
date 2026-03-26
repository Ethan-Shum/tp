<head-bottom>
  <link rel="stylesheet" href="{{baseUrl}}/stylesheets/main.css">
</head-bottom>

<header sticky>
  <navbar type="dark">
    <a slot="brand" href="{{baseUrl}}/index.html" title="Home" class="navbar-brand">LockedIn</a>
    <li><a href="{{baseUrl}}/index.html" class="nav-link">Home</a></li>
    <li><a href="{{baseUrl}}/UserGuide.html" class="nav-link">User Guide</a></li>
    <li><a href="{{baseUrl}}/DeveloperGuide.html" class="nav-link">Developer Guide</a></li>
    <li><a href="{{baseUrl}}/AboutUs.html" class="nav-link">About Us</a></li>
    <li><a href="https://github.com/AY2526S2-CS2103T-W12-2/tp" target="_blank" class="nav-link"><md>:fab-github:</md></a>
    </li>
    <li slot="right">
      <form class="navbar-form">
        <searchbar :data="searchData" placeholder="Search" :on-hit="searchCallback" menu-align-right></searchbar>
      </form>
    </li>
  </navbar>
</header>

<div id="flex-body">
  <nav id="site-nav">
    <div class="site-nav-top">
      <div class="fw-bold mb-2" style="font-size: 1.25rem;">Site Map</div>
    </div>
    <div class="nav-component slim-scroll">
      <site-nav>
* [Home]({{ baseUrl }}/index.html)
* [User Guide]({{ baseUrl }}/UserGuide.html) :expanded:
  * [About LockedIn]({{ baseUrl }}/UserGuide.html#about-lockedin)
  * [Quick Start]({{ baseUrl }}/UserGuide.html#quick-start)
  * [Features]({{ baseUrl }}/UserGuide.html#features) :expanded:
    * [Help Command]({{ baseUrl }}/UserGuide.html#help)
    * [Add Command]({{ baseUrl }}/UserGuide.html#add)
    * [List Command]({{ baseUrl }}/UserGuide.html#list)
    * [Edit Command]({{ baseUrl }}/UserGuide.html#edit)
    * [Next Command]({{ baseUrl }}/UserGuide.html#next)
    * [Copy Command]({{ baseUrl }}/UserGuide.html#copy)
    * [Alias Command]({{ baseUrl }}/UserGuide.html#alias)
    * [Unalias Command]({{ baseUrl }}/UserGuide.html#unalias)
    * [Alias-List Command]({{ baseUrl }}/UserGuide.html#alias-list)
    * [Find Command]({{ baseUrl }}/UserGuide.html#find)
    * [Delete Command]({{ baseUrl }}/UserGuide.html#delete)
    * [Clear Command]({{ baseUrl }}/UserGuide.html#clear)
    * [Exit Command]({{ baseUrl }}/UserGuide.html#exit)
  * [Saving and Editing Data]({{ baseUrl }}/UserGuide.html#saving-and-editing-data)
  * [FAQ]({{ baseUrl }}/UserGuide.html#faq)
  * [Known Issues]({{ baseUrl }}/UserGuide.html#known-issues)
  * [Command Summary]({{ baseUrl }}/UserGuide.html#command-summary)
* [Developer Guide]({{ baseUrl }}/DeveloperGuide.html) :expanded:
  * [Acknowledgements]({{ baseUrl }}/DeveloperGuide.html#acknowledgements)
  * [Setting Up]({{ baseUrl }}/DeveloperGuide.html#setting-up-getting-started)
  * [Design]({{ baseUrl }}/DeveloperGuide.html#design)
  * [Implementation]({{ baseUrl }}/DeveloperGuide.html#implementation)
  * [Documentation, logging, testing, configuration, dev-ops]({{ baseUrl }}/DeveloperGuide.html#documentation-logging-testing-configuration-dev-ops)
  * [Appendix: Requirements]({{ baseUrl }}/DeveloperGuide.html#appendix-requirements)
  * [Appendix: Instructions for manual testing]({{ baseUrl }}/DeveloperGuide.html#appendix-instructions-for-manual-testing)
* [About Us]({{ baseUrl }}/AboutUs.html)
      </site-nav>
    </div>
  </nav>
  <div id="content-wrapper">
    {{ content }}
  </div>
  <nav id="page-nav">
    <div class="nav-component slim-scroll">
      <page-nav />
    </div>
  </nav>
  <scroll-top-button></scroll-top-button>
</div>

<footer>
  <!-- Support MarkBind by including a link to us on your landing page! -->
  <div class="text-center">
    <small>[<md>**Powered by**</md> <img src="https://markbind.org/favicon.ico" width="30"> {{MarkBind}}, generated on {{timestamp}}]</small>
  </div>
</footer>
