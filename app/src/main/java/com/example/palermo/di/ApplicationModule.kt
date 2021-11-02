package coomecipar.example.palermo.di

import android.content.Context
import com.example.palermo.utils.DataRepo
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import coomecipar.example.palermo.Credential


fun ApplicationModule(context: Context) = Kodein.Module {
    bind<Context>() with instance(context)

    bind<Credential>() with singleton {
        Credential()
    }

    bind<DataRepo>() with singleton {
        DataRepo(instance())
    }

}