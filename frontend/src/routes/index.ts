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
 * Pages
 */
import Home from '@/pages/Home';
import Backoffice from '@/pages/Backoffice';

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
      {
        path: '/backoffice',
        Component: Backoffice,
      }
    ],
  },
]);

export default router;
