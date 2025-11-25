/**
 * Node modules
 */
import { createBrowserRouter } from 'react-router';
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

import Login from '@/pages/auth/Login';
import AgentHome from '@/components/dashboard/pages/AgentHome';


/**
 * Helpers
 */
function AdminProtected() {
  return (
    <ProtectedRoute roles={["AMMINISTRATORE"]}>
      <AdminDashboard />
    </ProtectedRoute>
  );
}

function AgentProtected() {
  return (
    <ProtectedRoute roles={["AGENTE"]}>
      <AgentHome />
    </ProtectedRoute>
  );
}

// Lazy import for bundle optimization

// const Evaluation = lazy(() => import('@/pages/Evaluation'));
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
      {
        path: 'admin/dashboard',
        Component: AdminProtected
      },
      {
        path: 'agent/dashboard',
        Component: AgentProtected,
      },
    ],
  },
]);

export default router;
