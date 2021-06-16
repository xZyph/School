import eventRoutes from './api/events/events-routes';
import authRoutes from './api/auth/auth-routes';
import registerRoutes from './api/register/register-routes';
import userRoutes from './api/user/user-routes';

export function registerEvents(app) {
  app.use("/api", eventRoutes)
  app.use("/api", authRoutes)
  app.use("/api", registerRoutes)
  app.use("/api", userRoutes)
}