import { Routes } from '@angular/router';
import {Home} from './components/home/home';

export const routes: Routes = [
  {path: '', component: Home},
  { path: 'auth', loadChildren: () =>
      import("./components/auth/auth.routes").then((routes)=> routes.authRoutes)}
];
