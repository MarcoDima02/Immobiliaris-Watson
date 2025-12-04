/**
 * Node modules
 */
import { createBrowserRouter, Outlet } from 'react-router';
import { lazy } from 'react';

/**
 * Components
 */
import RootLayout from '@/components/layout/RootLayout';

/**
 * Middleware
 */
import ProtectedRoute from '@/components/auth/ProtectedRoute';

/**
 * Pages
 */
import Home from '@/pages/Home';
import AdminDashboard from '@/pages/AdminDashboard';
import AgentDashboard from '@/pages/AgentDashboard';

import Login from '@/pages/auth/Login';
import AgentNavbar from '@/components/agentDashboard/agentNavbar';
import AgentRequestDetails from '@/pages/AgentRequestDetails';
import AgentMyRequests from '@/pages/AgentMyRequests';
import AdminNavbar from '@/components/adminDashboard/adminNavbar';


/**
 * Layout protetti
 */
function AdminProtected() {
  return (
    <ProtectedRoute roles={["AMMINISTRATORE"]}>
      <AdminNavbar />

      <div className="ms-20 md:ms-65 mt-5">
        <Outlet />
      </div>
    </ProtectedRoute>
  );
}

function AgentProtected() {
  return (
    <ProtectedRoute roles={["AGENTE"]}>
      <AgentNavbar />

      <div className="ms-20 md:ms-65 mt-5">
        <Outlet />
      </div>
    </ProtectedRoute>
  );
}

// Lazy import for bundle optimization
const NotFound = lazy(() => import('@/pages/NotFound'));

const router = createBrowserRouter([
  {
    path: '/',
    Component: RootLayout,
    ErrorBoundary: NotFound,
    children: [
      {
        index: true,
        Component: Home,
      },
    ],
  },

  {
    path: '/login',
    Component: Login,
  },

  {
    path: '/backoffice',
    children: [
      /**
       * ADMIN
       */
      {
        path: 'admin',
        Component: AdminProtected,
        children: [
          {
            path: 'dashboard',
            element: <AdminDashboard type={"dashboard"} />
          },
          {
            path: 'utenti',
            element: <AdminDashboard type={"utente"} />
          },
          {
            path: 'contratti',
            element: <AdminDashboard type={"contratto"} />
          },
          {
            path: 'richieste',
            element: <AdminDashboard type={"richiesta"} />
          },
          {
            path: 'richiesta',
            element: <AgentRequestDetails />
          }
        ]
      },

      /**
       * AGENTE
       */
      {
        path: 'agent',
        Component: AgentProtected,
        children: [
          {
            path: 'dashboard',
            Component: AgentDashboard
          },
          {
            path: 'myRequests/:filter',
            Component: AgentMyRequests
          },
          {
            path: 'request',
            Component: AgentRequestDetails
          }
        ]
      },
    ],
  },
]);

export default router;
