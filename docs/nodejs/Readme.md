# Project Signer - NodeJS Troubleshooting


### 1. Node JS Upgrade - Missing types

#### Symptom - project [international-airports-service-root](https://bitbucket.org/jesperancinha/international-airports-service-root)

```js
yarn build
yarn run v1.22.15
$ tsc server.ts && ng build
node_modules/@types/express/index.d.ts:58:55 - error TS2344: Type 'Response<any, Record<string, any>>' does not satisfy the constraint 'ServerResponse'.
  Type 'Response<any, Record<string, any>>' is missing the following properties from type 'ServerResponse': statusCode, statusMessage, assignSocket, detachSocket, and 54 more.

58     var static: serveStatic.RequestHandlerConstructor<Response>;
                                                         ~~~~~~~~

node_modules/@types/express/index.d.ts:98:18 - error TS2694: Namespace '"express-serve-static-core"' has no exported member 'ParamsDictionary'.

98         P = core.ParamsDictionary,
                    ~~~~~~~~~~~~~~~~

node_modules/@types/express/index.d.ts:101:25 - error TS2694: Namespace '"express-serve-static-core"' has no exported member 'Query'.

101         ReqQuery = core.Query,
                            ~~~~~

node_modules/@types/express/index.d.ts:103:15 - error TS2315: Type 'ErrorRequestHandler' is not generic.

103     > extends core.ErrorRequestHandler<P, ResBody, ReqBody, ReqQuery, Locals> {}
                  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

node_modules/@types/express/index.d.ts:113:18 - error TS2694: Namespace '"express-serve-static-core"' has no exported member 'ParamsDictionary'.

113         P = core.ParamsDictionary,
                     ~~~~~~~~~~~~~~~~

node_modules/@types/express/index.d.ts:116:25 - error TS2694: Namespace '"express-serve-static-core"' has no exported member 'Query'.

116         ReqQuery = core.Query,
                            ~~~~~

node_modules/@types/express/index.d.ts:118:15 - error TS2315: Type 'Request' is not generic.

```

#### Solution

There are two names of the same library these days for some and that can lead to issues like this. In my specific case I had both `    "@types/express-serve-static-core": "^4.17.28"` and `express-serve-static-core": "0.1.1`. The solution was to remove the latter.