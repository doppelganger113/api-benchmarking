# --------------> The build image
FROM node:14.17.3 AS build
ARG NPM_TOKEN
WORKDIR /usr/src/app
COPY . /usr/src/app/

RUN npm ci
RUN npm run build

# Production install
RUN rm -rf ./node_modules
RUN echo "//registry.npmjs.org/:_authToken=$NPM_TOKEN" > .npmrc && \
   npm ci --only=production && \
   rm -f .npmrc

# --------------> The production image
FROM node:lts-alpine3.14
RUN apk add dumb-init
ENV NODE_ENV production
USER node
WORKDIR /usr/src/app
COPY --chown=node:node --from=build /usr/src/app/node_modules /usr/src/app/node_modules
COPY --chown=node:node --from=build /usr/src/app/dist /usr/src/app/
CMD ["dumb-init", "node", "--max-old-space-size=1536", "main.js"]
