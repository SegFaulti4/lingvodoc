package ru.ispras.lingvodoc.frontend

import com.greencatsoft.angularjs.{Angular, Config}
import com.greencatsoft.angularjs.core.{HttpProvider, Route, RouteProvider}
import ru.ispras.lingvodoc.frontend.app.controllers.modal._
import ru.ispras.lingvodoc.frontend.app.controllers.desktop.PerspectivePropertiesController
import ru.ispras.lingvodoc.frontend.app.controllers.{_}
import ru.ispras.lingvodoc.frontend.app.directives._
import ru.ispras.lingvodoc.frontend.app.services._

import scala.scalajs.js.annotation.JSExport

class DesktopApplicationConfig(routeProvider: RouteProvider, httpProvider: HttpProvider) extends Config {
  routeProvider
    .when("/", Route("/static/templates/home.html", "Lingvodoc 2.1", "HomeController"))
    .when("/login", Route("/static/templates/login.html", "Lingvodoc 2.1 / Login", "LoginController"))
    .when("/logout", Route("/static/templates/logout.html", "Lingvodoc 2.1 / Logout", "LogoutController"))
    .when("/dashboard", Route("/static/templates/dashboard.html", "Lingvodoc 2.1 / Dashboard", "DashboardController"))
    .when("/corpora", Route("/static/templates/corpora.html", "Lingvodoc 2.1 / Corpora", "CorporaController"))
    .when("/languages", Route("/static/templates/language.html", "Lingvodoc 2.1 / Languages", "LanguageController"))
    .when("/dictionary/:dictionaryClientId/:dictionaryObjectId/perspective/:perspectiveClientId/:perspectiveObjectId/view/:page?/:sortBy?", Route("/static/templates/viewDictionary.html", "Lingvodoc 2.1 / View", "ViewDictionaryController"))
    .when("/dictionary/:dictionaryClientId/:dictionaryObjectId/perspective/:perspectiveClientId/:perspectiveObjectId/edit/:page?/:sortBy?", Route("/static/templates/editDictionary.html", "Lingvodoc 2.1 / Edit", "EditDictionaryController"))
    .when("/dictionary/:dictionaryClientId/:dictionaryObjectId/perspective/:perspectiveClientId/:perspectiveObjectId/contributions/:page?/:sortBy?", Route("/static/templates/contributions.html", "Lingvodoc 2.1 / Contributions", "ContributionsController"))
    .when("/dictionary/create", Route("/static/templates/createDictionary.html", "CreateDictionary", "CreateDictionaryController"))
    .when("/corpora/create", Route("/static/templates/createCorpus.html", "Lingvodoc 2.1 / Create corpus", "CreateCorpusController"))
    .when("/files", Route("/static/templates/files.html", "Lingvodoc 2.1 / Files", "UserFilesController"))
    .when("/map_search", Route("/static/templates/mapSearch.html", "Lingvodoc 2.1 / Map search", "MapSearchController"))
    .otherwise(Route("/static/templates/404.html"))
}


@JSExport
object DesktopApplication {

  @JSExport
  def main(): Unit = {
    Angular.module("LingvodocDesktopModule", Seq("ngRoute", "ngSanitize", "ngAnimate", "ui.bootstrap"))
      .config[DesktopApplicationConfig]
      .factory[BackendServiceFactory]
      .factory[UserServiceFactory]
      .controller[desktop.NavigationController]
      .controller[desktop.LoginController]
      .controller[LogoutController]
      .controller[SignupController]
      .controller[DashboardController]
      .controller[LanguageController]
      .controller[desktop.HomeController]
      .controller[CreateLanguageController]
      .controller[CreateDictionaryController]
      .controller[CreateCorpusController]
      .controller[EditDictionaryModalController]
      .controller[ViewDictionaryModalController]
      .controller[PerspectivePropertiesController]
      .controller[DictionaryPropertiesController]
      .controller[CreatePerspectiveModalController]
      .controller[desktop.EditDictionaryController]
      .controller[PerspectiveMapController]
      .controller[ViewDictionaryController]
      .controller[SoundMarkupController]
      .controller[ExceptionHandlerController]
      .controller[CreateFieldController]
      .controller[EditDictionaryRolesModalController]
      .controller[EditPerspectiveRolesModalController]
      .controller[UserFilesController]
      .controller[MapSearchController]
      .controller[ViewInfoBlobsController]
      .controller[EditGroupingTagModalController]
      .controller[ContributionsController]
      .controller[CorporaController]
      .controller[ConvertEafController]
      .controller[DownloadEmbeddedBlobController]
      .controller[MessageController]
      .controller[UserProfileController]
      .controller[PerspectiveStatisticsModalController]
      .controller[DictionaryStatisticsModalController]
      .controller[PerspectivePhonologyModalController]
      .directive[ConvertToNumberDirective]
      .directive[OnReadFileDirective]
      .directive[OnReadDirective]
      .directive[TranslatableDirective]
      .directive[WaveSurferDirective]
      .directive[IndeterminateCheckboxDirective]
      .directive[DataLinkDirective]
      .run[AppInitializer]
  }
}
